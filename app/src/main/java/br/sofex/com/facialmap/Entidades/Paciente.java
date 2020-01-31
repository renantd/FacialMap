package br.sofex.com.facialmap.Entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

//@Entity
@Entity(foreignKeys = {@ForeignKey(entity = Usuario.class,parentColumns = "CodUsuario",childColumns = "CodigoUsuarioFK",onDelete = ForeignKey.CASCADE)},
indices = {@Index(name = "PacienteId_index", value = {"CodigoUsuarioFK"})})
public class Paciente {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CodPaciente")
    private Long CodPaciente;

    @NonNull
    @ColumnInfo(name = "NomePaciente")
    private String NomePaciente;

    @NonNull
    @ColumnInfo(name = "Documento")
    private String Documento;

    @NonNull
    @ColumnInfo(name = "DocumentoConteudo")
    private String DocumentoConteudo;

    @NonNull
    @ColumnInfo(name = "Nacionalidade")
    private String Nacionalidade;

    @NonNull
    @ColumnInfo(name = "Etnia")
    private String Etnia;

    @NonNull
    @ColumnInfo(name = "DataNascimento")
    private String DataNascimento; //TODO: Sqllite  não suporta Date

    @NonNull
    @ColumnInfo(name = "Sexo")
    private String Sexo;

    @NonNull
    @ColumnInfo(name = "TelefoneFixo")
    private String TelefoneFixo;

    @NonNull
    @ColumnInfo(name = "TelefoneCelular")
    private String TelefoneCelular;

    @NonNull
    @ColumnInfo(name = "Email")
    private String Email;

    @NonNull
    @ColumnInfo(name = "Cep")
    private String Cep;

    @NonNull
    @ColumnInfo(name = "Endereco")
    private String Endereco;

    @NonNull
    @ColumnInfo(name = "Complemento")
    private String Complemento;

    @NonNull
    @ColumnInfo(name = "Numero")
    private Integer Numero;

    @NonNull
    @ColumnInfo(name = "Bairro")
    private String Bairro;

    @NonNull
    @ColumnInfo(name = "Municipio")
    private String Municipio;

    @NonNull
    @ColumnInfo(name = "Estado")
    private String Estado;

    @NonNull
    @ColumnInfo(name = "Foto")
    private byte[] Foto;

    @NonNull
    @ColumnInfo(name = "Foto_Nome")
    private String Foto_Nome;


    @NonNull
    @ColumnInfo(name = "CodigoUsuarioFK")
    private Long CodigoUsuarioFK;



    @NonNull
    public Long getCodPaciente() {
        return CodPaciente;
    }

    public void setCodPaciente(@NonNull Long codPaciente) {
        CodPaciente = codPaciente;
    }

    @NonNull
    public String getNomePaciente() {
        return NomePaciente;
    }

    public void setNomePaciente(@NonNull String nomePaciente) {
        NomePaciente = nomePaciente;
    }

    @NonNull
    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(@NonNull String documento) {
        Documento = documento;
    }

    @NonNull
    public String getDocumentoConteudo() {
        return DocumentoConteudo;
    }

    public void setDocumentoConteudo(@NonNull String documentoConteudo) {
        DocumentoConteudo = documentoConteudo;
    }

    @NonNull
    public String getNacionalidade() {
        return Nacionalidade;
    }

    public void setNacionalidade(@NonNull String nacionalidade) {
        Nacionalidade = nacionalidade;
    }

    @NonNull
    public String getEtnia() {
        return Etnia;
    }

    public void setEtnia(@NonNull String etnia) {
        Etnia = etnia;
    }

    @NonNull
    public String getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(@NonNull String dataNascimento) {
        DataNascimento = dataNascimento;
    }

    @NonNull
    public String getSexo() {
        return Sexo;
    }

    public void setSexo(@NonNull String sexo) {
        Sexo = sexo;
    }

    @NonNull
    public String getTelefoneFixo() {
        return TelefoneFixo;
    }

    public void setTelefoneFixo(@NonNull String telefoneFixo) {
        TelefoneFixo = telefoneFixo;
    }

    @NonNull
    public String getTelefoneCelular() {
        return TelefoneCelular;
    }

    public void setTelefoneCelular(@NonNull String telefoneCelular) {
        TelefoneCelular = telefoneCelular;
    }

    @NonNull
    public String getEmail() {
        return Email;
    }

    public void setEmail(@NonNull String email) {
        Email = email;
    }

    @NonNull
    public String getCep() {
        return Cep;
    }

    public void setCep(@NonNull String cep) {
        Cep = cep;
    }

    @NonNull
    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(@NonNull String endereco) {
        Endereco = endereco;
    }

    @NonNull
    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(@NonNull String complemento) {
        Complemento = complemento;
    }

    @NonNull
    public Integer getNumero() {
        return Numero;
    }

    public void setNumero(@NonNull Integer numero) {
        Numero = numero;
    }

    @NonNull
    public String getBairro() {
        return Bairro;
    }

    public void setBairro(@NonNull String bairro) {
        Bairro = bairro;
    }

    @NonNull
    public String getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(@NonNull String municipio) {
        Municipio = municipio;
    }

    @NonNull
    public String getEstado() {
        return Estado;
    }

    public void setEstado(@NonNull String estado) {
        Estado = estado;
    }

    @NonNull
    public byte[] getFoto() {
        return Foto;
    }

    public void setFoto(@NonNull byte[] foto) {
        Foto = foto;
    }

    @NonNull
    public String getFoto_Nome() {
        return Foto_Nome;
    }

    public void setFoto_Nome(@NonNull String foto_Nome) {
        Foto_Nome = foto_Nome;
    }

    @NonNull
    public Long getCodigoUsuarioFK() {
        return CodigoUsuarioFK;
    }

    public void setCodigoUsuarioFK(@NonNull Long codigoUsuarioFK) {
        CodigoUsuarioFK = codigoUsuarioFK;
    }

    public Paciente(String NomePaciente, String Documento, String DocumentoConteudo, String Nacionalidade, String Etnia, String DataNascimento, String Sexo, String TelefoneFixo, String TelefoneCelular,
        String Email, String Cep, String Endereco, String Complemento, Integer Numero, String Bairro, String Municipio, String Estado, byte[] Foto, String Foto_Nome, Long CodPaciente){

        this.NomePaciente = NomePaciente; this.Documento = Documento; this.DocumentoConteudo = DocumentoConteudo; this.Nacionalidade = Nacionalidade; this.Etnia = Etnia;
        this.DataNascimento = DataNascimento; this.Sexo = Sexo; this.TelefoneFixo = TelefoneFixo; this.TelefoneCelular = TelefoneCelular; this.Email = Email;
        this.Cep = Cep; this.Endereco = Endereco; this.Complemento = Complemento; this.Numero = Numero; this.Bairro = Bairro; this.Municipio = Municipio;
        this.Estado = Estado; this.Foto = Foto; this.Foto_Nome = Foto_Nome; this.CodigoUsuarioFK = CodPaciente;

    }

}
