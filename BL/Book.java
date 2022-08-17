package BL;

import java.util.List;

public class Book {
    private int id;
    private final List<Author> authors;
    private final String title;
    private final int release_year;
    private final String genre;


    public Book(int id, List<Author> authors, String title, int release_year, String genre) {
        this.id=id;
        this.authors = authors;
        this.title = title;
        this.release_year = release_year;
        this.genre = genre;
    }

    public Book(List<Author> authors, String title, int release_year, String genre) {
        this.authors = authors;
        this.title = title;
        this.release_year = release_year;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public int getRelease_year() {
        return release_year;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString(){
        String buf="";
        StringBuilder bufS=new StringBuilder(buf);
        bufS.append("ID: ").append(id).append("\n");
        bufS.append("Title: ").append(title).append("\n");
        bufS.append("Release year: ").append(release_year).append("\n");
        bufS.append("Genre: ").append(genre).append("\n");
        bufS.append("Authors list: ").append("\n");
        for (Author element : authors) {
            bufS.append(element.toString());
        }
        bufS.append("\n");
        buf=bufS.toString();
        return buf;
    }

}
