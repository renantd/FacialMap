package br.sofex.com.facialmap.Entidades;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(foreignKeys = {@ForeignKey(entity = Mapa.class,parentColumns = "CodMapa",childColumns = "CodigoMapaFK",onDelete = ForeignKey.CASCADE)},
indices = {@Index(name = "PontoId_index", value = {"CodigoMapaFK"})})
public class Ponto {

    @NonNull
    @PrimaryKey//(autoGenerate = true)
    @ColumnInfo(name = "CodPonto")
    private Long CodPonto;

    @NonNull
    @ColumnInfo(name = "NomePonto")
    private String NomePonto;

    @NonNull
    @ColumnInfo(name = "CoordenadaX")
    private Integer CoordenadaX;

    @NonNull
    @ColumnInfo(name = "CoordenadaY")
    private Integer CoordenadaY;


    @NonNull
    @ColumnInfo(name = "CodigoMapaFK")
    private Long CodigoMapaFK;


    @NonNull
    public Long getCodPonto() {
        return CodPonto;
    }

    public void setCodPonto(@NonNull Long codPonto) {
        CodPonto = codPonto;
    }

    @NonNull
    public String getNomePonto() {
        return NomePonto;
    }

    public void setNomePonto(@NonNull String nomePonto) {
        NomePonto = nomePonto;
    }

    @NonNull
    public Integer getCoordenadaX() {
        return CoordenadaX;
    }

    public void setCoordenadaX(@NonNull Integer coordenadaX) {
        CoordenadaX = coordenadaX;
    }

    @NonNull
    public Integer getCoordenadaY() {
        return CoordenadaY;
    }

    public void setCoordenadaY(@NonNull Integer coordenadaY) {
        CoordenadaY = coordenadaY;
    }

    @NonNull
    public Long getCodigoMapaFK() {
        return CodigoMapaFK;
    }

    public void setCodigoMapaFK(@NonNull Long codigoMapaFK) {
        CodigoMapaFK = codigoMapaFK;
    }

    public Ponto(String NomePonto, Integer  CoordenadaX, Integer CoordenadaY, Long CodMap){
        this.NomePonto = NomePonto; this.CoordenadaX = CoordenadaX;
        this.CoordenadaY = CoordenadaY; this.CodigoMapaFK = CodMap;
    }

    public Ponto(){}

}
