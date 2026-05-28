package com.bitwig.extensions.controllers.arturia.keylab.mk3;

import java.util.UUID;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

public class KeylabMk3ControllerExtensionDefinition extends ControllerExtensionDefinition {
    private static final UUID DRIVER_ID = UUID.fromString("d3f9c2a1-7b84-4e6d-9f10-2a5c8b7e1d43");

    // 2- KeyLab 61 mk3 MIDI
    // 2- KeyLab 61 mk3 DAW
    private static final String MIDI_NAME_FORMAT_WINDOWS = "KeyLab %d mk3 MIDI";
    private static final String DAW_NAME_FORMAT_WINDOWS = "KeyLab %d mk3 DAW";

    private static final String MIDI_NAME_FORMAT_MAC = "KeyLab %d mk3 MIDI";
    private static final String DAW_NAME_FORMAT_MAC = "KeyLab %d mk3 DAW";

    private static final String MIDI_NAME_FORMAT_LINUX = "KeyLab %d mk3 KeyLab %d mk3 MID";
    private static final String DAW_NAME_FORMAT_LINUX = "KeyLab %d mk3 KeyLab %d mk3 DAW";


    private static final int[] KEY_VARS = {49, 61, 88};

    public KeylabMk3ControllerExtensionDefinition() {
    }

    @Override
    public String getName() {
        return "KeyLab Mk3";
    }

    @Override
    public String getAuthor() {
        return "Stephan Anft";
    }

    @Override
    public String getVersion() {
        return "1.00";
    }

    @Override
    public UUID getId() {
        return DRIVER_ID;
    }

    @Override
    public String getHardwareVendor() {
        return "Arturia";
    }

    @Override
    public String getHardwareModel() {
        return "KeyLab Mk3";
    }

    @Override
    public int getRequiredAPIVersion() {
        return 18;
    }

    @Override
    public int getNumMidiInPorts() {
        return 2;
    }

    @Override
    public int getNumMidiOutPorts() {
        return 2;
    }

    @Override
    public String getHelpFilePath() {
        return "Controllers/Arturia/Arturia KeyLab mk3.pdf";
    }

    @Override
    public void listAutoDetectionMidiPortNames(final AutoDetectionMidiPortNamesList list,
        final PlatformType platformType) {
        for (final int keys : KEY_VARS) {
            if (platformType == PlatformType.WINDOWS) {
                final String dawPortName = DAW_NAME_FORMAT_WINDOWS.formatted(keys);
                final String midiPortName = MIDI_NAME_FORMAT_WINDOWS.formatted(keys);
                list.add(
                    new String[] {dawPortName, midiPortName},//
                    new String[] {dawPortName, midiPortName});
                for (int i = 2; i < 6; i++) {
                    final String dawPort = "%d- %s".formatted(i, dawPortName);
                    final String midiPort = "%d- %s".formatted(i, midiPortName);
                    list.add(
                        new String[] {dawPort, midiPort},//
                        new String[] {dawPort, midiPort});
                }
            } else if (platformType == PlatformType.LINUX) {
                final String dawPortName = DAW_NAME_FORMAT_LINUX.formatted(keys, keys);
                final String midiPortName = MIDI_NAME_FORMAT_LINUX.formatted(keys, keys);
                list.add(
                    new String[] {dawPortName, midiPortName},//
                    new String[] {dawPortName, midiPortName});
            } else {
                final String dawPortName = DAW_NAME_FORMAT_MAC.formatted(keys);
                final String midiPortName = MIDI_NAME_FORMAT_MAC.formatted(keys);
                list.add(
                    new String[] {dawPortName, midiPortName},//
                    new String[] {dawPortName, midiPortName});
            }
        }
    }


    private void addPorts(final AutoDetectionMidiPortNamesList list, final String format, final int numberOfKeys,
        final int appendWindowsCount) {
        final String portName = String.format(format, numberOfKeys);
        list.add(new String[] {portName}, new String[] {portName});
        for (int i = 0; i < appendWindowsCount; i++) {
            final String portAppended = "%d- %s".formatted(i + 2, portName);
            list.add(new String[] {portAppended}, new String[] {portAppended});
        }
    }


    @Override
    public KeylabMk3ControllerExtension createInstance(final ControllerHost host) {
        return new KeylabMk3ControllerExtension(this, host);
    }
}
