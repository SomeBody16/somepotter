package network.something.somepotter.client.speech;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import network.something.somepotter.SomePotter;
import network.something.somepotter.init.MessageInit;
import network.something.somepotter.packet.PacketCastSpell;
import network.something.somepotter.spell.spells.basic_cast.BasicCastSpell;

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
    private volatile boolean skipCast = false;
    private volatile String defaultSpellId = BasicCastSpell.ID;

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
                var speechResult = recognizer.getResult();
                var spellId = speechResult == null
                        ? defaultSpellId
                        : speechResult.getHypothesis().toLowerCase();

                pauseRecognition();
                if (skipCast) {
                    skipCast = false;
                    continue;
                }

                castSpell(spellId);
                defaultSpellId = BasicCastSpell.ID;
            }
        } catch (IOException e) {
            SomePotter.LOGGER.error("Speech Thread failed", e);
        }
        SomePotter.LOGGER.info("Speech Thread stopped");
    }

    public void castSpell(String spellId) {
        Minecraft.getInstance().execute(() -> {
            var packetCastSpell = new PacketCastSpell(spellId);
            MessageInit.sendToServer(packetCastSpell);
        });
    }

    public void skip() {
        skipCast = true;
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

    public void setDefaultSpellId(String defaultSpellId) {
        this.defaultSpellId = defaultSpellId;
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
