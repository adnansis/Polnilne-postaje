package si.fri.prpo.storitve.dtos;

public class SporociloDTO {

    private String sporocilo;
    private Integer postaja_id;
    private String datetime;

    public SporociloDTO() {}

    public SporociloDTO(String sporocilo, Integer postaja_id, String datetime) {
        this.sporocilo = sporocilo;
        this.postaja_id = postaja_id;
        this.datetime = datetime;
    }

    public String getSporocilo() {
        return sporocilo;
    }

    public void setSporocilo(String sporocilo) {
        this.sporocilo = sporocilo;
    }

    public Integer getPostaja_id() {
        return postaja_id;
    }

    public void setPostaja_id(Integer postaja_id) {
        this.postaja_id = postaja_id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String porociloToString() {
        return getSporocilo() + " Čas: " + getDatetime();
    }
}
