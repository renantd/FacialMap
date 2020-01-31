package br.sofex.com.facialmap.Entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(foreignKeys = {@ForeignKey(entity = Ponto.class,parentColumns = "CodPonto",childColumns = "CodPontoFK",onDelete = ForeignKey.CASCADE)},
indices = {@Index(name = "AplicacaoId_index", value = {"CodPontoFK"})})
public class Aplicacao {

    @NonNull
    @PrimaryKey//(autoGenerate = true)
    @ColumnInfo(name = "CodAplicacao")
    private Long CodAplicacao;

    @NonNull
    @ColumnInfo(name = "NomeToxina")
    private String NomeToxina;

    @NonNull
    @ColumnInfo(name = "FabricanteToxina")
    private String FabricanteToxina;

    @NonNull
    @ColumnInfo(name = "DistribuidoraToxina")
    private String DistribuidoraToxina;

    @NonNull
    @ColumnInfo(name = "TamanhoFrascoToxina")
    private Integer TamanhoFrascoToxina;

    @NonNull
    @ColumnInfo(name = "SoroFisiologico")
    private String SoroFisiologico;

    @NonNull
    @ColumnInfo(name = "VolumeAplicacao")
    private Integer VolumeAplicacao;

    @NonNull
    @ColumnInfo(name = "DiametroAgulha")
    private Integer DiametroAgulha;

    @NonNull
    @ColumnInfo(name = "ComprimentoAgulha")
    private Integer ComprimentoAgulha;

    @NonNull
    @ColumnInfo(name = "DataValidade")
    private String DataValidade;

    @NonNull
    @ColumnInfo(name = "LoteToxina")
    private String LoteToxina;

    @NonNull
    @ColumnInfo(name = "CodPontoFK")
    private Long CodPontoFK;


    @NonNull
    public Long getCodAplicacao() {
        return CodAplicacao;
    }

    public void setCodAplicacao(@NonNull Long codAplicacao) {
        CodAplicacao = codAplicacao;
    }

    @NonNull
    public String getNomeToxina() {
        return NomeToxina;
    }

    public void setNomeToxina(@NonNull String nomeToxina) {
        NomeToxina = nomeToxina;
    }

    @NonNull
    public String getFabricanteToxina() {
        return FabricanteToxina;
    }

    public void setFabricanteToxina(@NonNull String fabricanteToxina) {
        FabricanteToxina = fabricanteToxina;
    }

    @NonNull
    public String getDistribuidoraToxina() {
        return DistribuidoraToxina;
    }

    public void setDistribuidoraToxina(@NonNull String distribuidoraToxina) {
        DistribuidoraToxina = distribuidoraToxina;
    }

    @NonNull
    public Integer getTamanhoFrascoToxina() {
        return TamanhoFrascoToxina;
    }

    public void setTamanhoFrascoToxina(@NonNull Integer tamanhoFrascoToxina) {
        TamanhoFrascoToxina = tamanhoFrascoToxina;
    }

    @NonNull
    public String getSoroFisiologico() {
        return SoroFisiologico;
    }

    public void setSoroFisiologico(@NonNull String soroFisiologico) {
        SoroFisiologico = soroFisiologico;
    }

    @NonNull
    public Integer getVolumeAplicacao() {
        return VolumeAplicacao;
    }

    public void setVolumeAplicacao(@NonNull Integer volumeAplicacao) {
        VolumeAplicacao = volumeAplicacao;
    }

    @NonNull
    public Integer getDiametroAgulha() {
        return DiametroAgulha;
    }

    public void setDiametroAgulha(@NonNull Integer diametroAgulha) {
        DiametroAgulha = diametroAgulha;
    }

    @NonNull
    public Integer getComprimentoAgulha() {
        return ComprimentoAgulha;
    }

    public void setComprimentoAgulha(@NonNull Integer comprimentoAgulha) {
        ComprimentoAgulha = comprimentoAgulha;
    }

    @NonNull
    public String getDataValidade() {
        return DataValidade;
    }

    public void setDataValidade(@NonNull String dataValidade) {
        DataValidade = dataValidade;
    }

    @NonNull
    public String getLoteToxina() {
        return LoteToxina;
    }

    public void setLoteToxina(@NonNull String loteToxina) {
        LoteToxina = loteToxina;
    }

    @NonNull
    public Long getCodPontoFK() {
        return CodPontoFK;
    }

    public void setCodPontoFK(@NonNull Long codPontoFK) {
        CodPontoFK = codPontoFK;
    }


    public Aplicacao(String NomeToxina, String FabricanteToxina, String DistribuidoraToxina, Integer TamanhoFrascoToxina, String SoroFisiologico,
        Integer VolumeAplicacao, Integer DiametroAgulha, Integer ComprimentoAgulha, String DataValidade, String LoteToxina, Long CodPontoFK){

        this.NomeToxina = NomeToxina;
        this.FabricanteToxina = FabricanteToxina;
        this.DistribuidoraToxina = DistribuidoraToxina;
        this.TamanhoFrascoToxina = TamanhoFrascoToxina;
        this.SoroFisiologico = SoroFisiologico;
        this.VolumeAplicacao = VolumeAplicacao;
        this.DiametroAgulha = DiametroAgulha;
        this.ComprimentoAgulha = ComprimentoAgulha;
        this.DataValidade = DataValidade;
        this.LoteToxina = LoteToxina;
        this.CodPontoFK = CodPontoFK;

    }

}
