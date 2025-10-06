package pojos;

public class POJO_expBodyPOJO_JPH {
    private int bookingid;
    private POJO_reqBodyPOJO_JPH booking;

    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public POJO_reqBodyPOJO_JPH getBooking() {
        return booking;
    }

    public void setBooking(POJO_reqBodyPOJO_JPH booking) {
        this.booking = booking;
    }

    public POJO_expBodyPOJO_JPH(int bookingid, POJO_reqBodyPOJO_JPH booking) {
        this.bookingid = bookingid;
        this.booking = booking;
    }

    public POJO_expBodyPOJO_JPH() {
    }

    @Override
    public String toString() {
        return "POJO_expBodyPOJO_JPH{" +
                "bookingid=" + bookingid +
                ", booking=" + booking +
                '}';
    }
}