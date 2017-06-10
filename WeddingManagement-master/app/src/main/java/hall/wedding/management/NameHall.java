package hall.wedding.management;

/**
 * Created by Vu Khac Hoi on 3/4/2017.
 */

public class NameHall {
    private  int BanToiDa;

    public NameHall() {
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
    private  boolean isVisible;

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    private  boolean isActive;
    private  boolean isCheck;
    public String getNamehall() {
        return Namehall;
    }

    public void setNamehall(String namehall) {
        Namehall = namehall;
    }

    public int getImgActive() {
        return imgActive;
    }

    public void setImgActive(int imgActive) {
        this.imgActive = imgActive;
    }

    public NameHall(int banToiDa, boolean isVisible, boolean isActive, boolean isCheck, int imgActive, String namehall, int giaToiThieu) {
        BanToiDa = banToiDa;
        this.isVisible = isVisible;
        this.isActive = isActive;
        this.isCheck = isCheck;
        this.imgActive = imgActive;
        Namehall = namehall;
        GiaToiThieu = giaToiThieu;
    }

    private  int imgActive;
    private String Namehall;

    public boolean isActive() {

        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }



    public int getBanToiDa() {
        return BanToiDa;
    }

    public void setBanToiDa(int banToiDa) {
        BanToiDa = banToiDa;
    }

    public int getGiaToiThieu() {
        return GiaToiThieu;
    }

    public void setGiaToiThieu(int giaToiThieu) {
        GiaToiThieu = giaToiThieu;
    }

    private  int GiaToiThieu;
}
