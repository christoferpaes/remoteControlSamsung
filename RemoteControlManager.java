pimport com.samsung.android.sdk.tv.SmartViewManager;
import com.samsung.android.sdk.tv.tvcontrol.TVControlManager;
import com.samsung.android.sdk.tv.tvcontrol.TVControlManager.VolumeChangeListener;

public class RemoteControlManager {
    private TVControlManager tvControlManager;
    private VolumeChangeListener volumeChangeListener;

    public RemoteControlManager() {
        tvControlManager = TVControlManager.getInstance();
    }

    public void connect(RemoteDevice device) {
        tvControlManager.connect(device.getIp());
    }

    public void setVolumeUp(ResultCallback callback) {
        tvControlManager.sendKeyCode(TVControlManager.KEYCODE_VOLUME_UP, callback);
    }

    public void setVolumeDown(ResultCallback callback) {
        tvControlManager.sendKeyCode(TVControlManager.KEYCODE_VOLUME_DOWN, callback);
    }

    public void setMute(ResultCallback callback) {
        tvControlManager.sendKeyCode(TVControlManager.KEYCODE_MUTE, callback);
    }

    public void openMenu(ResultCallback callback) {
        tvControlManager.sendKeyCode(TVControlManager.KEYCODE_MENU, callback);
    }

    public void openSearch(ResultCallback callback) {
        tvControlManager.sendKeyCode(TVControlManager.KEYCODE_SEARCH, callback);
    }

    public void setFastForward(ResultCallback callback) {
        tvControlManager.sendKeyCode(TVControlManager.KEYCODE_MEDIA_FAST_FORWARD, callback);
    }

    public void setPause(ResultCallback callback) {
        tvControlManager.sendKeyCode(TVControlManager.KEYCODE_MEDIA_PAUSE, callback);
    }

    public void setRewind(ResultCallback callback) {
        tvControlManager.sendKeyCode(TVControlManager.KEYCODE_MEDIA_REWIND, callback);
    }

    public void setPlay(ResultCallback callback) {
        tvControlManager.sendKeyCode(TVControlManager.KEYCODE_MEDIA_PLAY, callback);
    }

    public void sendNumber(int number, ResultCallback callback) {
        int keyCode = getNumberKeyCode(number);
        tvControlManager.sendKeyCode(keyCode, callback);
    }

    public void disconnect() {
        tvControlManager.disconnect();
    }

    private int getNumberKeyCode(int number) {
        int keyCode = -1;
        switch (number) {
            case 0:
                keyCode = TVControlManager.KEYCODE_0;
                break;
            case 1:
                keyCode = TVControlManager.KEYCODE_1;
                break;
            case 2:
                keyCode = TVControlManager.KEYCODE_2;
                break;
            case 3:
                keyCode = TVControlManager.KEYCODE_3;
                break;
            case 4:
                keyCode = TVControlManager.KEYCODE_4;
                break;
            case 5:
                keyCode = TVControlManager.KEYCODE_5;
                break;
            case 6:
                keyCode = TVControlManager.KEYCODE_6;
                break;
            case 7:
                keyCode = TVControlManager.KEYCODE_7;
                break;
            case 8:
                keyCode = TVControlManager.KEYCODE_8;
                break;
            case 9:
                keyCode = TVControlManager.KEYCODE_9;
                break;
        }
        return keyCode;
    }

    public interface ResultCallback {
        void onSuccess();
        void onError(int errorCode);
    }
}
