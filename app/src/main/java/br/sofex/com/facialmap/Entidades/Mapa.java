package br.sofex.com.facialmap.Entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(foreignKeys = {@ForeignKey(entity = Paciente.class,parentColumns = "CodPaciente",childColumns = "CodigoPacienteFK",onDelete = ForeignKey.CASCADE)},
indices = {@Index(name = "MapaId_index", value = {"CodigoPacienteFK"})})
public class Mapa {

    @NonNull
    @PrimaryKey//(autoGenerate = true)
    @ColumnInfo(name = "CodMapa")
    private Long CodMapa;

    @NonNull
    @ColumnInfo(name = "DataCriacao")
    private String DataCriacao;

    @NonNull
    @ColumnInfo(name = "HoraCriacao")
    private String HoraCriacao;

    @NonNull
    @ColumnInfo(name = "TipoDispositivo")
    private String TipoDispositivo;


    @NonNull
    @ColumnInfo(name = "FotoMapaInicial")
    private byte[] FotoMapaInicial;

    @NonNull
    @ColumnInfo(name = "FotoMapaInicialNome")
    private String FotoMapaInicialNome;


    @NonNull
    @ColumnInfo(name = "FotoMapeada")
    private byte[] FotoMapeada;

    @NonNull
    @ColumnInfo(name = "FotoMapeadaNome")
    private String FotoMapeadaNome;


    @NonNull
    @ColumnInfo(name = "FotoFinal")
    private byte[] FotoFinal;

    @NonNull
    @ColumnInfo(name = "FotoFinalNome")
    private String FotoFinalNome;


    @NonNull
    @ColumnInfo(name = "FotoVertical")
    private byte[] FotoVertical;

    @NonNull
    @ColumnInfo(name = "FotoVerticalNome")
    private String FotoVerticalNome;


    @NonNull
    @ColumnInfo(name = "FotoHorizontal")
    private byte[] FotoHorizontal;

    @NonNull
    @ColumnInfo(name = "FotoHorizontalNome")
    private String FotoHorizontalNome;


    @NonNull
    @ColumnInfo(name = "CodigoPacienteFK")
    private Long CodigoPacienteFK;


    @NonNull
    public Long getCodMapa() {
        return CodMapa;
    }

    public void setCodMapa(@NonNull Long codMapa) {
        CodMapa = codMapa;
    }

    @NonNull
    public String getDataCriacao() {
        return DataCriacao;
    }

    public void setDataCriacao(@NonNull String dataCriacao) {
        DataCriacao = dataCriacao;
    }

    @NonNull
    public String getHoraCriacao() {
        return HoraCriacao;
    }

    public void setHoraCriacao(@NonNull String horaCriacao) {
        HoraCriacao = horaCriacao;
    }

    @NonNull
    public String getTipoDispositivo() {
        return TipoDispositivo;
    }

    public void setTipoDispositivo(@NonNull String tipoDispositivo) {
        TipoDispositivo = tipoDispositivo;
    }

    @NonNull
    public byte[] getFotoMapaInicial() {
        return FotoMapaInicial;
    }

    public void setFotoMapaInicial(@NonNull byte[] fotoMapaInicial) {
        FotoMapaInicial = fotoMapaInicial;
    }

    @NonNull
    public String getFotoMapaInicialNome() {
        return FotoMapaInicialNome;
    }

    public void setFotoMapaInicialNome(@NonNull String fotoMapaInicialNome) {
        FotoMapaInicialNome = fotoMapaInicialNome;
    }

    @NonNull
    public byte[] getFotoMapeada() {
        return FotoMapeada;
    }

    public void setFotoMapeada(@NonNull byte[] fotoMapeada) {
        FotoMapeada = fotoMapeada;
    }

    @NonNull
    public String getFotoMapeadaNome() {
        return FotoMapeadaNome;
    }

    public void setFotoMapeadaNome(@NonNull String fotoMapeadaNome) {
        FotoMapeadaNome = fotoMapeadaNome;
    }

    @NonNull
    public byte[] getFotoFinal() {
        return FotoFinal;
    }

    public void setFotoFinal(@NonNull byte[] fotoFinal) {
        FotoFinal = fotoFinal;
    }

    @NonNull
    public String getFotoFinalNome() {
        return FotoFinalNome;
    }

    public void setFotoFinalNome(@NonNull String fotoFinalNome) {
        FotoFinalNome = fotoFinalNome;
    }

    @NonNull
    public byte[] getFotoVertical() {
        return FotoVertical;
    }

    public void setFotoVertical(@NonNull byte[] fotoVertical) {
        FotoVertical = fotoVertical;
    }

    @NonNull
    public String getFotoVerticalNome() {
        return FotoVerticalNome;
    }

    public void setFotoVerticalNome(@NonNull String fotoVerticalNome) {
        FotoVerticalNome = fotoVerticalNome;
    }

    @NonNull
    public byte[] getFotoHorizontal() {
        return FotoHorizontal;
    }

    public void setFotoHorizontal(@NonNull byte[] fotoHorizontal) {
        FotoHorizontal = fotoHorizontal;
    }

    @NonNull
    public String getFotoHorizontalNome() {
        return FotoHorizontalNome;
    }

    public void setFotoHorizontalNome(@NonNull String fotoHorizontalNome) {
        FotoHorizontalNome = fotoHorizontalNome;
    }

    @NonNull
    public Long getCodigoPacienteFK() {
        return CodigoPacienteFK;
    }

    public void setCodigoPacienteFK(@NonNull Long codigoPacienteFK) {
        CodigoPacienteFK = codigoPacienteFK;
    }

    public Mapa(String DataCriacao, String  HoraCriacao, String TipoDispositivo,
        byte[] FotoMapaInicial, String  FotoMapaInicialNome, byte[] FotoMapeada, String  FotoMapeadaNome, byte[] FotoFinal, String FotoFinalNome,
        byte[] FotoVertical, String  FotoVerticalNome, byte[] FotoHorizontal, String  FotoHorizontalNome , long CodPaciente){

        this.DataCriacao = DataCriacao; this.HoraCriacao = HoraCriacao; this.TipoDispositivo = TipoDispositivo;
        this.FotoMapaInicial = FotoMapaInicial; this.FotoMapaInicialNome = FotoMapaInicialNome;
        this.FotoMapeada = FotoMapeada; this.FotoMapeadaNome = FotoMapeadaNome;
        this.FotoFinal = FotoFinal; this.FotoFinalNome = FotoFinalNome;
        this.FotoVertical = FotoVertical; this.FotoVerticalNome = FotoVerticalNome;
        this.FotoHorizontal = FotoHorizontal; this.FotoHorizontalNome = FotoHorizontalNome;
        this.CodigoPacienteFK = CodPaciente;
    }

    public Mapa(){}

}
