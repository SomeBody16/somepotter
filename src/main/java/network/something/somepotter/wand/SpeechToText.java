package network.something.somepotter.wand;

import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import network.something.somepotter.SomePotter;

import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;
import java.util.ArrayList;

public class SpeechToText extends Thread {

    public static void listen() {
        if (isRecording) {
            SomePotter.LOGGER.info("Already recording!");
            return;
        }
        isRecording = true;
        SomePotter.LOGGER.info("Spell cast!");

        var thread = new SpeechToText();
        thread.start();
    }

    protected static boolean isRecording = false;

    protected SpeechToText() {
        super("SpeechToText");
    }

    @Override
    public void run() {
        ResponseObserver<StreamingRecognizeResponse> responseObserver = null;
        try (SpeechClient client = SpeechClient.create()) {

            responseObserver = new ResponseObserver<>() {
                ArrayList<StreamingRecognizeResponse> responses = new ArrayList<>();

                public void onStart(StreamController controller) {
                }

                public void onResponse(StreamingRecognizeResponse response) {
                    responses.add(response);
                }

                public void onComplete() {
                    for (StreamingRecognizeResponse response : responses) {
                        StreamingRecognitionResult result = response.getResultsList().get(0);
                        SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                        SomePotter.LOGGER.info("Transcript : {}\n", alternative.getTranscript());
                    }
                    isRecording = false;
                }

                public void onError(Throwable t) {
                    t.printStackTrace();
                    SomePotter.LOGGER.error(t.getMessage());
                    isRecording = false;
                }
            };

            var clientStream = client.streamingRecognizeCallable().splitCall(responseObserver);

            var recognitionConfig = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                    .setLanguageCode("en-US")
                    .setSampleRateHertz(16000)
                    .build();

            var streamingRecognitionConfig = StreamingRecognitionConfig.newBuilder().setConfig(recognitionConfig).build();

            var request = StreamingRecognizeRequest.newBuilder()
                    .setStreamingConfig(streamingRecognitionConfig)
                    .build(); // The first request in a streaming call has to be a config

            clientStream.send(request);
            // SampleRate:16000Hz, SampleSizeInBits: 16, Number of channels: 1, Signed: true,
            // bigEndian: false
            AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);
            DataLine.Info targetInfo = new Info(
                    TargetDataLine.class,
                    audioFormat); // Set the system information to read from the microphone audio stream

            if (!AudioSystem.isLineSupported(targetInfo)) {
                SomePotter.LOGGER.error("Microphone not supported");
                System.exit(0);
            }
            // Target data line captures the audio stream the microphone produces.
            TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();
            SomePotter.LOGGER.info("Start speaking");
            long startTime = System.currentTimeMillis();
            // Audio Input Stream
            AudioInputStream audio = new AudioInputStream(targetDataLine);
            while (true) {
                long estimatedTime = System.currentTimeMillis() - startTime;
                byte[] data = new byte[6400];
                audio.read(data);
                if (estimatedTime > 5000) { // 60 seconds
                    SomePotter.LOGGER.info("Stop speaking.");
                    targetDataLine.stop();
                    targetDataLine.close();
                    break;
                }
                request =
                        StreamingRecognizeRequest.newBuilder()
                                .setAudioContent(ByteString.copyFrom(data))
                                .build();
                clientStream.send(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            SomePotter.LOGGER.error(e.getMessage());
        }
        responseObserver.onComplete();
        isRecording = false;
    }
}
