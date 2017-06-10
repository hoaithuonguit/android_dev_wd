package hall.wedding.management;

/**
 * Created by Vu Khac Hoi on 2/27/2017.
 */

public class HallDetail {

    private   String NameHall;
    private  boolean IsActive;

    public HallDetail() {
    }

    public String getNameHall() {
        return NameHall;
    }

    public void setNameHall(String nameHall) {
        NameHall = nameHall;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public HallDetail(String nameHall, boolean isActive) {

        NameHall = nameHall;
        IsActive = isActive;
    }
}
