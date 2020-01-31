package br.sofex.com.facialmap.Entidades;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CodUsuario")
    private int CodUsuario;

    @NonNull
    @ColumnInfo(name = "Nome_Usuario")
    private String Nome_Usuario;

    @NonNull
    @ColumnInfo(name = "Login")
    private String Login;

    @NonNull
    @ColumnInfo(name = "Senha")
    private String Senha;

    @NonNull
    @ColumnInfo(name = "Email")
    private String Email;

    @NonNull
    @ColumnInfo(name = "Foto")
    private byte[] Foto;

    @NonNull
    @ColumnInfo(name = "NomeFotoUsuario")
    private String NomeFotoUsuario;


    public int getCodUsuario() {
        return CodUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        CodUsuario = codUsuario;
    }

    @NonNull
    public String getNome_Usuario() {
        return Nome_Usuario;
    }

    public void setNome_Usuario(@NonNull String nome_Usuario) {
        Nome_Usuario = nome_Usuario;
    }

    @NonNull
    public String getLogin() {
        return Login;
    }

    public void setLogin(@NonNull String login) {
        Login = login;
    }

    @NonNull
    public String getSenha() {
        return Senha;
    }

    public void setSenha(@NonNull String senha) {
        Senha = senha;
    }

    @NonNull
    public String getEmail() {
        return Email;
    }

    public void setEmail(@NonNull String email) {
        Email = email;
    }

    @NonNull
    public byte[] getFoto() {
        return Foto;
    }

    public void setFoto(@NonNull byte[] foto) {
        Foto = foto;
    }

    @NonNull
    public String getNomeFotoUsuario() {
        return NomeFotoUsuario;
    }

    public void setNomeFotoUsuario(@NonNull String nomeFotoUsuario) {
        NomeFotoUsuario = nomeFotoUsuario;
    }

    public Usuario(String Nome_Usuario, String Login, String Senha, String Email, byte[] Foto, String NomeFotoUsuario){
        //this.CodUsuario = CodUsuario;
        this.Nome_Usuario = Nome_Usuario;
        this.Login = Login;
        this.Senha = Senha;
        this.Email = Email;
        this.Foto = Foto;
        this.NomeFotoUsuario = NomeFotoUsuario;
    }


}
