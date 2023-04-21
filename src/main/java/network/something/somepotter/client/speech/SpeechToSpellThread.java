package network.something.somepotter.client.speech;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import network.something.somepotter.SomePotter;
import network.something.somepotter.init.MessageInit;
import network.something.somepotter.packet.PacketCastSpell;

import java.io.IOException;

@OnlyIn(Dist.CLIENT)
public class SpeechToSpellThread extends Thread {
    private static SpeechToSpellThread instance = null;

    public static SpeechToSpellThread getInstance() {
        if (instance == null) {
            instance = new SpeechToSpellThread();
            instance.setName("Speech To Spell Thread");
        }
        return instance;
    }

    private volatile boolean isRunning = true;
    private volatile boolean isRecognizing = false;

    @Override
    public void run() {
        SomePotter.LOGGER.info("Speech Thread started");
        try {
            LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(getConfiguration());
            recognizer.startRecognition(true);

            while (isRunning) {
                while (!isRecognizing) {
                    Thread.yield();
                }
                SomePotter.LOGGER.info("Starting recognition...");
                var speechResult = recognizer.getResult();
                if (speechResult == null) {
                    SomePotter.LOGGER.info("Spell not recognized");
                    continue;
                }

                var spellName = speechResult.getHypothesis().toLowerCase();
                SomePotter.LOGGER.info("Spell is: '{}'", spellName);

                Minecraft.getInstance().execute(() -> {
                    SomePotter.LOGGER.info("Sending spell: '{}'", spellName);
                    var packetCastSpell = new PacketCastSpell(spellName);
                    MessageInit.sendToServer(packetCastSpell);
                    SomePotter.LOGGER.info("PacketCastSpell sent");
                });
            }
        } catch (IOException e) {
            SomePotter.LOGGER.error("Speech Thread failed", e);
        }
        SomePotter.LOGGER.info("Speech Thread stopped");
    }

    public void pauseRecognition() {
        isRecognizing = false;
    }

    public void resumeRecognition() {
        isRecognizing = true;
    }

    public void stopRecognition() {
        isRecognizing = false;
        isRunning = false;
    }

    protected Configuration getConfiguration() {
        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        configuration.setDictionaryPath("resource:/assets/somepotter/speech/spells.dict");
        configuration.setLanguageModelPath("resource:/assets/somepotter/speech/spells.lm");

//        configuration.setUseGrammar(true);
//        configuration.setGrammarName("spells");
//        configuration.setGrammarPath("resource:/assets/speech/spells.gram");

        return configuration;
    }
}
