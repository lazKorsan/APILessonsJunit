package pojos;

public class POJO_pojoBody_JPH {
    /*
            "title":"Ahmet",
            "body":"Merhaba",
            "userId":10,
            "id":70
     */
    //1-Tüm variable'lar private olarak oluşturulur
    private String title;
    private String body;
    private int userId;
    private int id;

    //2-Tüm variable'lar için getter() ve setter() uygulanır

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //3-Tüm parametreleri içeren bir constructor oluşturulur

    public POJO_pojoBody_JPH(String title, String body, int userId, int id) {
        this.title = title;
        this.body = body;
        this.userId = userId;
        this.id = id;
    }

    //4-Parametresiz bir constructor oluşturulur

    public POJO_pojoBody_JPH() {
    }

    //5-toString Metodu uygulanır

    @Override
    public String toString() {
        return "Request Body Bilgilieri:" +
                "Title Değeri: " + title + ' ' +
                "Body Değeri: " + body + ' ' +
                "User Id Değeri: " + userId +
                "Id Değeri: " + id +
                ' ';
    }
}