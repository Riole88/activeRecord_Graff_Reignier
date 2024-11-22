public class Film {
    private int id;
    private String titre;
    private int id_rea;

    public Film(String titre, Personne p){
        this.id = -1;
        this.titre = titre;
        this.id_rea = p.getId();
    }

    private Film(int id, String titre, int id_rea){
        this.id = id;
        this.titre = titre;
        this.id_rea = id_rea;
    }
}
