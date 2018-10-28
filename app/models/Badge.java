package models;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import io.ebean.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Badge extends Model{

    @Id
    private Integer id;
    private String name;
    private String description;
    private String category;
    private String date;
    private Integer points;

    public static Finder<Integer, Badge> find = new Finder<>(Badge.class);

   /* public Badge(){

    }

    public Badge(Integer id, String name, String description, String category, Integer points, String date){
        this.id=id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.points = points;
        this.date = date;



    }

    private static Set<Badge> badges;

    static{
        badges = new HashSet<>();
        Badge badge1= new Badge(1, "Definir e seguir políticas de uso do Git no leia-me do projeto (mínimo: estrutura de pastas e regras de commit)", "Lorem ipsum dolor sit amet", "CM", 1, "271018");
        Badge badge2= new Badge(2,"Garantir que todas as funcionalidades tenham pelo menos um commit (atômico) e que este seja rastreável.", "Lorem ipsum dolor sit amet", "CM", 3, "271018");
        Badge badge3= new Badge(3,"Usar a funcionalidade de \"Tag\" e \"Release\" para documentar a baseline do sistema", "Lorem ipsum dolor sit amet", "Verification", 2, "281018");
        Badge badge4= new Badge(4,"Documentar a estrutura de alto nível do sistema (ex: Diagrama de Componentes)", "Lorem ipsum dolor sit amet", "Projeto e Modelagem", 1, "281018");
        Badge badge5= new Badge(5,"Documentar código com comentários", "Lorem ipsum dolor sit amet", "Implementação e Construção", 2, "271118" );
        Badge badge6= new Badge(6,"Construir software coerente com requisitos e design", "Lorem ipsum dolor sit amet", "Implementação e Construção", 3, "271118");

        badges.add(badge1);
        badges.add(badge2);
        badges.add(badge3);
        badges.add(badge4);
        badges.add(badge5);
        badges.add(badge6);

    }

    public static Set<Badge> allBadges(){
        return badges;
    }

    public static Badge findById(Integer id) {
        for (Badge badge: badges){
            if (id.equals(badge.getId())){
                return badge;
            }
        }
    
        return null;
    }

    public static void add(Badge badge){
        badges.add(badge);
    }

    public static boolean remove(Badge badge) {
        return badges.remove(badge);
    }
*/
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
