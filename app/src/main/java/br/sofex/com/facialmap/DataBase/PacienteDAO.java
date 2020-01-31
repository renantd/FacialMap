package br.sofex.com.facialmap.DataBase;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.sofex.com.facialmap.Entidades.Paciente;

@Dao
public interface PacienteDAO {

    //TODO: method to insert a record in Table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPaciente(Paciente paciente);


    //TODO: Get Listas
    @Query("SELECT CodPaciente FROM Paciente")
    List<Long> GetCodPacienteList();

    @Query("SELECT NomePaciente FROM Paciente")
    List<String> GetNomePacienteList();

    @Query("SELECT Documento FROM Paciente")
    List<String> GetDocumentoList();

    @Query("SELECT DocumentoConteudo FROM Paciente")
    List<String> GetDocumentoConteudo();

    @Query("SELECT Nacionalidade FROM Paciente")
    List<String> GetNacionalidadeList();

    @Query("SELECT Etnia FROM Paciente")
    List<String> GetEtniaList();

    @Query("SELECT DataNascimento FROM Paciente")
    List<String> GetDataNascList();

    @Query("SELECT Sexo FROM Paciente")
    List<String> GetSexoList();

    @Query("SELECT TelefoneFixo FROM Paciente")
    List<String> GetTelFixoList();

    @Query("SELECT TelefoneCelular FROM Paciente")
    List<String> GetTelCelularList();

    @Query("SELECT Email FROM Paciente")
    List<String> GetEmailList();

    //TODO: CEP

    @Query("SELECT Cep FROM Paciente")
    List<String> GetCepList();

    @Query("SELECT Endereco FROM Paciente")
    List<String> GetEnderecoList();

    @Query("SELECT Complemento FROM Paciente")
    List<String> GetComplementoList();

    @Query("SELECT Numero FROM Paciente")
    List<Integer> GetNumeroList();

    @Query("SELECT Bairro FROM Paciente")
    List<String> GetBairroList();

    @Query("SELECT Municipio FROM Paciente")
    List<String> GetMunicipioList();

    @Query("SELECT Estado FROM Paciente")
    List<String> GetEstadoList();


    @Query("SELECT Foto FROM Paciente")
    List<byte[]> GetFotoPacienteList();

    @Query("SELECT Foto_Nome FROM Paciente")
    List<String> GetFotoNomePacienteList();




    //TODO: Get Listas
    @Query("SELECT NomePaciente FROM Paciente")
    List<String> GetListNomePacienteByNome();



    //TODO: Get Listas Por Nome
    @Query("SELECT CodPaciente FROM Paciente where NomePaciente = :NomePaciente")
    List<Long> GetCodPacienteListByNome(String NomePaciente);

    @Query("SELECT NomePaciente FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetNomePacienteListByNome(String NomePaciente);

    @Query("SELECT Documento FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetDocumentoListByNome(String NomePaciente);

    @Query("SELECT DocumentoConteudo FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetDocumentoConteudoByNome(String NomePaciente);

    @Query("SELECT Nacionalidade FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetNacionalidadeListByNome(String NomePaciente);

    @Query("SELECT Etnia FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetEtniaListByNome(String NomePaciente);

    @Query("SELECT DataNascimento FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetDataNascListByNome(String NomePaciente);

    @Query("SELECT Sexo FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetSexoListByNome(String NomePaciente);

    @Query("SELECT TelefoneFixo FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetTelFixoListByNome(String NomePaciente);

    @Query("SELECT TelefoneCelular FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetTelCelularListByNome(String NomePaciente);

    @Query("SELECT Email FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetEmailListByNome(String NomePaciente);

    //TODO: CEP

    @Query("SELECT Cep FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetCepListByNome(String NomePaciente);

    @Query("SELECT Endereco FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetEnderecoListByNome(String NomePaciente);

    @Query("SELECT Complemento FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetComplementoListByNome(String NomePaciente);

    @Query("SELECT Numero FROM Paciente where NomePaciente = :NomePaciente")
    List<Integer> GetNumeroListByNome(String NomePaciente);

    @Query("SELECT Bairro FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetBairroListByNome(String NomePaciente);

    @Query("SELECT Municipio FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetMunicipioListByNome(String NomePaciente);

    @Query("SELECT Estado FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetEstadoListByNome(String NomePaciente);

    @Query("SELECT Foto FROM Paciente where NomePaciente = :NomePaciente")
    List<byte[]> GetFotoPacienteListByNome(String NomePaciente);

    @Query("SELECT Foto_Nome FROM Paciente where NomePaciente = :NomePaciente")
    List<String> GetFotoNomePacienteListByNome(String NomePaciente);



    //TODO: Get Listas Por Codigo(Primary Key)
    @Query("SELECT CodPaciente FROM Paciente where CodPaciente = :CodPaciente")
    List<Long> GetCodPacienteListByCod(Long CodPaciente);

    @Query("SELECT NomePaciente FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetNomePacienteListByCod(Long CodPaciente);

    @Query("SELECT Documento FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetDocumentoListByCod(Long CodPaciente);

    @Query("SELECT DocumentoConteudo FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetDocumentoConteudoByCod(Long CodPaciente);

    @Query("SELECT Nacionalidade FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetNacionalidadeListByCod(Long CodPaciente);

    @Query("SELECT Etnia FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetEtniaListByCod(Long CodPaciente);

    @Query("SELECT DataNascimento FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetDataNascListByCod(Long CodPaciente);

    @Query("SELECT Sexo FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetSexoListByCod(Long CodPaciente);

    @Query("SELECT TelefoneFixo FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetTelFixoListByCod(Long CodPaciente);

    @Query("SELECT TelefoneCelular FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetTelCelularListByCod(Long CodPaciente);

    @Query("SELECT Email FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetEmailListByCod(Long CodPaciente);

    //TODO: CEP

    @Query("SELECT Cep FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetCepListByCod(Long CodPaciente);

    @Query("SELECT Endereco FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetEnderecoListByCod(Long CodPaciente);

    @Query("SELECT Complemento FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetComplementoListByCod(Long CodPaciente);

    @Query("SELECT Numero FROM Paciente where CodPaciente = :CodPaciente")
    List<Integer> GetNumeroListByCod(Long CodPaciente);

    @Query("SELECT Bairro FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetBairroListByCod(Long CodPaciente);

    @Query("SELECT Municipio FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetMunicipioListByCod(Long CodPaciente);

    @Query("SELECT Estado FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetEstadoListByCod(Long CodPaciente);

    @Query("SELECT Foto FROM Paciente where CodPaciente = :CodPaciente")
    List<byte[]> GetFotoPacienteListByCod(Long CodPaciente);

    @Query("SELECT Foto_Nome FROM Paciente where CodPaciente = :CodPaciente")
    List<String> GetFotoNomePacienteListByCod(Long CodPaciente);





    //TODO: Get Dados Por Codigo(Primary Key)
    @Query("SELECT CodPaciente FROM Paciente where CodPaciente = :CodPaciente")
    Long GetCodPacienteByCod(Long CodPaciente);

    @Query("SELECT NomePaciente FROM Paciente where CodPaciente = :CodPaciente")
    String GetNomePacienteByCod(Long CodPaciente);

    @Query("SELECT Documento FROM Paciente where CodPaciente = :CodPaciente")
    String GetDocumentoByCod(Long CodPaciente);

    @Query("SELECT DocumentoConteudo FROM Paciente where CodPaciente = :CodPaciente")
    String GetDocumentoConteudoPacienteByCod(Long CodPaciente);

    @Query("SELECT Nacionalidade FROM Paciente where CodPaciente = :CodPaciente")
    String GetNacionalidadeByCod(Long CodPaciente);

    @Query("SELECT Etnia FROM Paciente where CodPaciente = :CodPaciente")
    String GetEtniaByCod(Long CodPaciente);

    @Query("SELECT DataNascimento FROM Paciente where CodPaciente = :CodPaciente")
    String GetDataNascByCod(Long CodPaciente);

    @Query("SELECT Sexo FROM Paciente where CodPaciente = :CodPaciente")
    String GetSexoByCod(Long CodPaciente);

    @Query("SELECT TelefoneFixo FROM Paciente where CodPaciente = :CodPaciente")
    String GetTelFixoByCod(Long CodPaciente);

    @Query("SELECT TelefoneCelular FROM Paciente where CodPaciente = :CodPaciente")
    String GetTelCelularByCod(Long CodPaciente);

    @Query("SELECT Email FROM Paciente where CodPaciente = :CodPaciente")
    String GetEmailByCod(Long CodPaciente);

    //TODO: CEP
    @Query("SELECT Cep FROM Paciente where CodPaciente = :CodPaciente")
    String GetCepByCod(Long CodPaciente);

    @Query("SELECT Endereco FROM Paciente where CodPaciente = :CodPaciente")
    String GetEnderecoByCod(Long CodPaciente);

    @Query("SELECT Complemento FROM Paciente where CodPaciente = :CodPaciente")
    String GetComplementoByCod(Long CodPaciente);

    @Query("SELECT Numero FROM Paciente where CodPaciente = :CodPaciente")
    Integer GetNumeroByCod(Long CodPaciente);

    @Query("SELECT Bairro FROM Paciente where CodPaciente = :CodPaciente")
    String GetBairroByCod(Long CodPaciente);

    @Query("SELECT Municipio FROM Paciente where CodPaciente = :CodPaciente")
    String GetMunicipioByCod(Long CodPaciente);

    @Query("SELECT Estado FROM Paciente where CodPaciente = :CodPaciente")
    String GetEstadoByCod(Long CodPaciente);

    @Query("SELECT Foto FROM Paciente where CodPaciente = :CodPaciente")
    byte[] GetFotoPacienteByCod(Long CodPaciente);

    @Query("SELECT Foto_Nome FROM Paciente where CodPaciente = :CodPaciente")
    String GetFotoNomePacienteByCod(Long CodPaciente);



    //TODO: Get Dados Por Nome do Paciente
    @Query("SELECT CodPaciente FROM Paciente where NomePaciente = :NomePaciente")
    Long GetCodPacienteByNome(String NomePaciente);

    @Query("SELECT NomePaciente FROM Paciente where NomePaciente = :NomePaciente")
    String GetNomePacienteByNome(String NomePaciente);

    @Query("SELECT Documento FROM Paciente where NomePaciente = :NomePaciente")
    String GetDocumentoByNome(String NomePaciente);

    @Query("SELECT DocumentoConteudo FROM Paciente where NomePaciente = :NomePaciente")
    String GetDocumentoConteudoPacienteByNome(String NomePaciente);

    @Query("SELECT Nacionalidade FROM Paciente where NomePaciente = :NomePaciente")
    String GetNacionalidadeByNome(String NomePaciente);

    @Query("SELECT Etnia FROM Paciente where NomePaciente = :NomePaciente")
    String GetEtniaByNome(String NomePaciente);

    @Query("SELECT DataNascimento FROM Paciente where NomePaciente = :NomePaciente")
    String GetDataNascByNome(String NomePaciente);

    @Query("SELECT Sexo FROM Paciente where NomePaciente = :NomePaciente")
    String GetSexoByNome(String NomePaciente);

    @Query("SELECT TelefoneFixo FROM Paciente where NomePaciente = :NomePaciente")
    String GetTelFixoByNome(String NomePaciente);

    @Query("SELECT TelefoneCelular FROM Paciente where NomePaciente = :NomePaciente")
    String GetTelCelularByNome(String NomePaciente);

    @Query("SELECT Email FROM Paciente where NomePaciente = :NomePaciente")
    String GetEmailByNome(String NomePaciente);

    //TODO: CEP
    @Query("SELECT Cep FROM Paciente where NomePaciente = :NomePaciente")
    String GetCepByNome(String NomePaciente);

    @Query("SELECT Endereco FROM Paciente where NomePaciente = :NomePaciente")
    String GetEnderecoByNome(String NomePaciente);

    @Query("SELECT Complemento FROM Paciente where NomePaciente = :NomePaciente")
    String GetComplementoByNome(String NomePaciente);

    @Query("SELECT Numero FROM Paciente where NomePaciente = :NomePaciente")
    Integer GetNumeroByNome(String NomePaciente);

    @Query("SELECT Bairro FROM Paciente where NomePaciente = :NomePaciente")
    String GetBairroByNome(String NomePaciente);

    @Query("SELECT Municipio FROM Paciente where NomePaciente = :NomePaciente")
    String GetMunicipioByNome(String NomePaciente);

    @Query("SELECT Estado FROM Paciente where NomePaciente = :NomePaciente")
    String GetEstadoByNome(String NomePaciente);

    @Query("SELECT Foto FROM Paciente where NomePaciente = :NomePaciente")
    byte[] GetFotoPacienteByNome(String NomePaciente);

    @Query("SELECT Foto_Nome FROM Paciente where NomePaciente = :NomePaciente")
    String GetFotoNomePacienteByNome(String NomePaciente);



    //TODO : Delete Paciente
    @Query("DELETE FROM Paciente where CodPaciente = :CodPaciente")
    void DeletePacienteByCod(Long CodPaciente);


    //TODO : Update Paciente
    @Query("UPDATE Paciente SET Foto =:b1 where CodPaciente = :CodPaciente")
    void  UpdateFotoPacByCod(byte[] b1,Long CodPaciente);


    @Query("UPDATE Paciente SET NomePaciente =:NewValue where CodPaciente = :CodPaciente")
    void  UpdateNomeByCod(String NewValue, Long CodPaciente);

    @Query("UPDATE Paciente SET Documento  =:NewValue where CodPaciente = :CodPaciente")
    void  UpdateDocumentoByCod(String NewValue, Long CodPaciente);

    @Query("UPDATE Paciente SET DocumentoConteudo =:NewValue where CodPaciente = :CodPaciente")
    void  UpdateDocConteudoByCod(String NewValue, Long CodPaciente);

    @Query("UPDATE Paciente SET Nacionalidade =:NewValue where CodPaciente = :CodPaciente")
    void  UpdateNacionalidadeByCod(String NewValue, Long CodPaciente);

    @Query("UPDATE Paciente SET Etnia =:NewValue where CodPaciente = :CodPaciente")
    void  UpdateEtniaByCod(String NewValue, Long CodPaciente);



    @Query("UPDATE Paciente SET DataNascimento =:NewValue where CodPaciente = :CodPaciente")
    void  UpdateDTNascByCod(String NewValue, Long CodPaciente);

    @Query("UPDATE Paciente SET Sexo =:NewValue where CodPaciente = :CodPaciente")
    void  UpdateSexoByCod(String NewValue, Long CodPaciente);

    @Query("UPDATE Paciente SET TelefoneFixo =:NewValue where CodPaciente = :CodPaciente")
    void  UpdateFixoByCod(String NewValue, Long CodPaciente);

    @Query("UPDATE Paciente SET TelefoneCelular =:NewValue where CodPaciente = :CodPaciente")
    void  UpdateCelularByCod(String NewValue, Long CodPaciente);

    @Query("UPDATE Paciente SET Email =:NewValue where CodPaciente = :CodPaciente")
    void  UpdateEmailByCod(String NewValue, Long CodPaciente);



    @Query("UPDATE Paciente SET Cep =:NewValue , Endereco =:NewValue2 , Complemento =:NewValue3 , Numero =:NewValue4 ," +
    " Bairro =:NewValue5 , Municipio =:NewValue6 , Estado =:NewValue7  where CodPaciente = :CodPaciente")
    void  UpdateCepByCod(String NewValue,String NewValue2,String NewValue3,String NewValue4,
    String NewValue5,String NewValue6,String NewValue7, Long CodPaciente);


    @Query("SELECT COUNT(CodPaciente) FROM Paciente")
     Integer TotalPacientes();


    //TODO: Shared Preference
    @Query("SELECT NomePaciente FROM Paciente,Usuario where CodUsuario = :CodUsuario AND NomePaciente = :NomePaciente")
    String GetNomePacByCodNome(Long CodUsuario,String NomePaciente);

}
