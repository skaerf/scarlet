package xyz.skaerf.scarlet.llm;

import java.time.Instant;

public class WorldContext {

    private Instant currentTime;
    private Provenance currentTimeProvenance;
    private String activeDevice;
    private Provenance activeDeviceProvenance;
    private String activeApplication;
    private Provenance activeApplicationProvenance;

    public WorldContext() {
    }

    public WorldContext(Instant currentTime, Provenance currentTimeProvenance, String activeDevice, Provenance activeDeviceProvenance, String activeApplication, Provenance activeApplicationProvenance) {
        this.currentTime = currentTime;
        this.currentTimeProvenance = currentTimeProvenance;
        this.activeDevice = activeDevice;
        this.activeDeviceProvenance = activeDeviceProvenance;
        this.activeApplication = activeApplication;
        this.activeApplicationProvenance = activeApplicationProvenance;
    }

    public Instant getCurrentTime() {
        return this.currentTime;
    }

    public void setCurrentTime(Instant currentTime) {
        this.currentTime = currentTime;
    }

    public Provenance getCurrentTimeProvenance() {
        return this.currentTimeProvenance;
    }

    public void setCurrentTimeProvenance(Provenance currentTimeProvenance) {
        this.currentTimeProvenance = currentTimeProvenance;
    }

    public String getActiveDevice() {
        return this.activeDevice;
    }

    public void setActiveDevice(String activeDevice) {
        this.activeDevice = activeDevice;
    }

    public Provenance getActiveDeviceProvenance() {
        return this.activeDeviceProvenance;
    }

    public void setActiveDeviceProvenance(Provenance activeDeviceProvenance) {
        this.activeDeviceProvenance = activeDeviceProvenance;
    }

    public String getActiveApplication() {
        return this.activeApplication;
    }

    public void setActiveApplication(String activeApplication) {
        this.activeApplication = activeApplication;
    }

    public Provenance getActiveApplicationProvenance() {
        return this.activeApplicationProvenance;
    }

    public void setActiveApplicationProvenance(Provenance activeApplicationProvenance) {
        this.activeApplicationProvenance = activeApplicationProvenance;
    }
}
