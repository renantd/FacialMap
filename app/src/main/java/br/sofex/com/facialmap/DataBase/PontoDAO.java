package br.sofex.com.facialmap.DataBase;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.sofex.com.facialmap.Entidades.Ponto;

@Dao
public interface PontoDAO {

    //TODO: method to insert a record in Table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPonto(Ponto ponto);

    @Query("SELECT NomePonto FROM Ponto,Mapa,Paciente WHERE NomePaciente = :NomePaciente And DataCriacao = :DataCriacao And HoraCriacao = :HoraCriacao ")
    String GetPontoByNomeDTHR(String NomePaciente, String DataCriacao, String HoraCriacao);


    @Query("SELECT CoordenadaX FROM Ponto,Mapa,Paciente WHERE NomePaciente = :NomePaciente And DataCriacao = :DataCriacao And HoraCriacao = :HoraCriacao ")
    Integer GetCoordXByNomeDTHR(String NomePaciente, String DataCriacao, String HoraCriacao);


    @Query("SELECT CoordenadaY FROM Ponto,Mapa,Paciente WHERE NomePaciente = :NomePaciente And DataCriacao = :DataCriacao And HoraCriacao = :HoraCriacao ")
    Integer GetCoordYByNomeDTHR(String NomePaciente, String DataCriacao, String HoraCriacao);



    @Query("select DISTINCT CodMapa FROM Ponto,Mapa,Paciente WHERE NomePaciente = :NomePaciente And DataCriacao = :DataCriacao And HoraCriacao = :HoraCriacao ")
    Long GetCodMapaByDTHR(String NomePaciente, String DataCriacao, String HoraCriacao);



    @Query("select count(CodPonto) FROM Ponto,Mapa,Paciente WHERE NomePaciente = :NomePaciente " +
    "And DataCriacao = :DataCriacao And HoraCriacao = :HoraCriacao And CodigoMapaFK = :CodMapFk")
     Integer TotalRowBY_DTHRCMF(String NomePaciente, String DataCriacao, String HoraCriacao, Long CodMapFk);



    @Query("select NomePonto FROM Ponto,Mapa,Paciente WHERE NomePaciente = :NomePaciente " +
    "And DataCriacao = :DataCriacao And HoraCriacao = :HoraCriacao And CodigoMapaFK = :CodMapFk")
     List<String> GetListNomePontosMapa(String NomePaciente, String DataCriacao, String HoraCriacao, Long CodMapFk);


    @Query("select CoordenadaX FROM Ponto,Mapa,Paciente WHERE NomePaciente = :NomePaciente " +
    "And DataCriacao = :DataCriacao And HoraCriacao = :HoraCriacao And CodigoMapaFK = :CodMapFk")
     List<Integer> GetListCoordXPontosMapa(String NomePaciente, String DataCriacao, String HoraCriacao, Long CodMapFk);


    @Query("select CoordenadaY FROM Ponto,Mapa,Paciente WHERE NomePaciente = :NomePaciente " +
    "And DataCriacao = :DataCriacao And HoraCriacao = :HoraCriacao And CodigoMapaFK = :CodMapFk")
     List<Integer> GetListCoordYPontosMapa(String NomePaciente, String DataCriacao, String HoraCriacao, Long CodMapFk);






    //TODO: Pegar CodMapaFK por NomePaciente , Data e Hora
    @Query("SELECT DISTINCT CodigoMapaFK FROM Mapa,Paciente,Ponto  WHERE NomePaciente = :NomePaciente and DataCriacao = :DataCriacao \n" +
    "and HoraCriacao = :HoraCriacao and CodMapa = CodigoMapaFK")
     Long GetCodMapaFK_ByDCHC(String NomePaciente, String DataCriacao, String HoraCriacao);






    //TODO: Pegar Listas
    @Query("SELECT NomePonto FROM Ponto,Mapa,Paciente WHERE NomePaciente = :NomePaciente And DataCriacao = :DataCriacao And HoraCriacao = :HoraCriacao ")
    List<String> GetListNomePontoDTHR(String NomePaciente, String DataCriacao, String HoraCriacao);


    @Query("SELECT CoordenadaX FROM Ponto,Mapa,Paciente WHERE NomePaciente = :NomePaciente And DataCriacao = :DataCriacao And HoraCriacao = :HoraCriacao ")
    List<Integer> GetListCoordXDTHR(String NomePaciente, String DataCriacao, String HoraCriacao);


    @Query("SELECT CoordenadaY FROM Ponto,Mapa,Paciente WHERE NomePaciente = :NomePaciente And DataCriacao = :DataCriacao And HoraCriacao = :HoraCriacao ")
    List<Integer> GetListCoordYDTHR(String NomePaciente, String DataCriacao, String HoraCriacao);




    @Query("select NomePonto from Ponto,Mapa,Paciente where NomePaciente = :NomePaciente \n" +
    "and DataCriacao = :DataCriacao and HoraCriacao = :HoraCriacao and CodigoMapaFK = :CodMapaFK")
     List<String> GetListNomePonto_DTHR_NPCMFK(String NomePaciente,String DataCriacao,String HoraCriacao,Long CodMapaFK);



    @Query("select DISTINCT CodigoMapaFK from Ponto,Mapa,Paciente " +
    "where NomePaciente = :NomePaciente and DataCriacao = :DataCriacao and HoraCriacao = :HoraCriacao ")
    Long GetListCodMapaFK_DTHR_NPCMFK(String NomePaciente,String DataCriacao,String HoraCriacao);



    @Query("select NomePonto from Ponto,Mapa,Paciente where NomePaciente = :NomePaciente \n" +
    "and DataCriacao = :DataCriacao and HoraCriacao = :HoraCriacao and NomePonto = :NomePonto " +
    "and CodigoMapaFK = :CodMapaFK")
    String GetNomePonto_DTHR_NPCMFK(String NomePaciente,String DataCriacao,String HoraCriacao,
    String NomePonto,Long CodMapaFK);


    @Query("select CoordenadaX from Ponto,Mapa,Paciente where NomePaciente = :NomePaciente \n" +
    "and DataCriacao = :DataCriacao and HoraCriacao = :HoraCriacao and NomePonto = :NomePonto " +
    "and CodigoMapaFK = :CodMapaFK")
    Integer GetCoordX_DTHR_NPCMFK(String NomePaciente,String DataCriacao,String HoraCriacao,
    String NomePonto,Long CodMapaFK);


    @Query("select CoordenadaY from Ponto,Mapa,Paciente where NomePaciente = :NomePaciente \n" +
    "and DataCriacao = :DataCriacao and HoraCriacao = :HoraCriacao and NomePonto = :NomePonto " +
    "and CodigoMapaFK = :CodMapaFK")
    Integer GetCoordY_DTHR_NPCMFK(String NomePaciente,String DataCriacao,String HoraCriacao,
    String NomePonto,Long CodMapaFK);


}
