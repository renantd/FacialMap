package br.sofex.com.facialmap.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.sofex.com.facialmap.Entidades.Usuario;

@Dao
public interface UsuarioDAO {

    //TODO: method to insert a record in Table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUuario(Usuario usuario);

    @Query("SELECT * FROM Usuario")
    List<Usuario> GetUsuario();

    @Query("SELECT Login FROM Usuario")
    List<String> GetUsuarioLogin();

    @Query("SELECT Senha FROM Usuario")
    List<String> GetUsuarioSenha();

    @Query("SELECT Nome_Usuario FROM Usuario")
    List<String> GetListNomeUsuario();


    @Query("SELECT CodUsuario FROM Usuario")
    List<Long> GetListCods();

    @Query("SELECT COUNT(CodUsuario) FROM Usuario")
    Integer GetNumberUser();



    @Query("SELECT CodUsuario FROM Usuario WHERE Login = :Login")
    Long GetCodUserByLogin(String Login);

    @Query("SELECT Nome_Usuario FROM Usuario WHERE Login = :Login")
    String GetNomeUserByLogin(String Login);

    @Query("SELECT Login FROM Usuario WHERE Login = :Login")
    String GetLoginUserByLogin(String Login);

    @Query("SELECT Senha FROM Usuario WHERE Login = :Login")
    String GetSenhaUserByLogin(String Login);

    @Query("SELECT Email FROM Usuario WHERE Login = :Login")
    String GetEmailUserByLogin(String Login);

    @Query("SELECT Foto FROM Usuario WHERE Login = :Login")
    byte[] GetFotoUserByLogin(String Login);



    @Query("SELECT CodUsuario FROM Usuario WHERE CodUsuario = :CodUsuario")
    Long GetCodUserByCod(Long CodUsuario);

    @Query("SELECT Nome_Usuario FROM Usuario WHERE CodUsuario = :CodUsuario")
    String GetNomeUserByCod(Long CodUsuario);

    @Query("SELECT Login FROM Usuario WHERE CodUsuario = :CodUsuario")
    String GetLoginUserByCod(Long CodUsuario);

    @Query("SELECT Senha FROM Usuario WHERE CodUsuario = :CodUsuario")
    String GetSenhaUserByCod(Long CodUsuario);

    @Query("SELECT Email FROM Usuario WHERE CodUsuario = :CodUsuario")
    String GetEmailUserByCod(Long CodUsuario);

    @Query("SELECT Foto FROM Usuario WHERE CodUsuario = :CodUsuario")
    byte[] GetFotoUserByCod(Long CodUsuario);



    //TODO: Update
    @Query("UPDATE Usuario set CodUsuario  = :NewValue  WHERE CodUsuario = :CodUsuario")
    void UpdateCodUserByCod(Long NewValue,Long CodUsuario);

    @Query("UPDATE Usuario set Nome_Usuario  = :NewValue   WHERE CodUsuario = :CodUsuario")
    void UpdateNomeUserByCod(String NewValue,Long CodUsuario);

    @Query("UPDATE Usuario set Login  = :NewValue   WHERE CodUsuario = :CodUsuario")
    void UpdateLoginUserByCod(String NewValue,Long CodUsuario);

    @Query("UPDATE Usuario set Senha  = :NewValue   WHERE CodUsuario = :CodUsuario")
    void UpdateSenhaUserByCod(String NewValue,Long CodUsuario);

    @Query("UPDATE Usuario set Email  = :NewValue   WHERE CodUsuario = :CodUsuario")
    void UpdateEmailUserByCod(String NewValue,Long CodUsuario);

    @Query("UPDATE Usuario set Foto  = :NewValue   WHERE CodUsuario = :CodUsuario")
    void UpdateFotoUserByCod(byte[] NewValue,Long CodUsuario);

}
