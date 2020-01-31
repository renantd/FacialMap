package br.sofex.com.facialmap.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.sofex.com.facialmap.Entidades.Mapa;

@Dao
public interface MapaDAO {

    //TODO: method to insert a record in Table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMapa(Mapa mapa);


    @Query("SELECT MAX(CodMapa) FROM Mapa,Ponto where CodMapa > CodigoMapaFK")
    Long GetLastRecordMapa();


    @Query("DELETE from Mapa where CodMapa = :CodMapa")
    void DeleteLastRecordMapa(Long CodMapa);


    @Query("SELECT DataCriacao FROM Mapa,Paciente WHERE CodPaciente = :CodPaciente")
    List<String> GetListDTCriacaoMapaByCod(Long CodPaciente);


    @Query("SELECT DataCriacao FROM Mapa,Paciente  WHERE CodPaciente = :CodPaciente")
    List<String> GetListHoraCriacaoMapaByCod(Long CodPaciente);


    @Query("SELECT Foto,FotoMapeada,FotoFinal,FotoVertical,FotoHorizontal FROM Mapa,Paciente  WHERE CodPaciente = :CodPaciente")
    List<byte[]> GetListFotosMapas(Long CodPaciente);



    //TODO: Lista de Fotos
    @Query("SELECT FotoMapaInicial FROM Mapa,Paciente  WHERE CodPaciente = :CodPaciente AND DataCriacao = :DataCriacao AND  HoraCriacao = :HoraCriacao")
    byte[] GetFotoMapaInicialByNome(Long CodPaciente,String DataCriacao, String HoraCriacao);


    @Query("SELECT FotoMapeada FROM Mapa,Paciente  WHERE CodPaciente = :CodPaciente AND DataCriacao = :DataCriacao AND  HoraCriacao = :HoraCriacao")
    byte[] GetFotoMapeadaByNome(Long CodPaciente,String DataCriacao, String HoraCriacao);


    @Query("SELECT FotoFinal FROM Mapa,Paciente  WHERE CodPaciente = :CodPaciente AND DataCriacao = :DataCriacao AND  HoraCriacao = :HoraCriacao")
    byte[] GetFotoFinalByNome(Long CodPaciente,String DataCriacao, String HoraCriacao);


    @Query("SELECT FotoVertical FROM Mapa,Paciente  WHERE CodPaciente = :CodPaciente AND DataCriacao = :DataCriacao AND  HoraCriacao = :HoraCriacao")
    byte[] GetFotoVerticalByNome(Long CodPaciente,String DataCriacao, String HoraCriacao);


    @Query("SELECT FotoHorizontal FROM Mapa,Paciente  WHERE CodPaciente = :CodPaciente AND DataCriacao = :DataCriacao AND  HoraCriacao = :HoraCriacao")
    byte[] GetFotoHorizontalByNome(Long CodPaciente,String DataCriacao, String HoraCriacao);




    @Query("SELECT CodPaciente FROM Paciente  WHERE NomePaciente = :NomePaciente")
    Long GetCodPacienteByNome(String NomePaciente);

    @Query("SELECT DataCriacao FROM Mapa,Paciente  WHERE CodPaciente = :CodPaciente")
    List<String> GetListDataMapasByCods(Long CodPaciente);

    @Query("SELECT HoraCriacao FROM Mapa,Paciente  WHERE CodPaciente = :CodPaciente")
    List<String> GetListHoraMapasByCods(Long CodPaciente);

    @Query("SELECT Foto,FotoMapeada,FotoFinal,FotoVertical,FotoHorizontal " +
    "FROM Mapa,Paciente  WHERE CodPaciente = :CodPaciente AND CodMapa = :CodMapa " +
    "AND DataCriacao = :DataCriacao AND HoraCriacao = :HoraCriacao")
    List<byte[]> GetListFotosMapasByCods(Long CodPaciente,Long CodMapa,String DataCriacao,String HoraCriacao );


    @Query("SELECT CodMapa FROM Mapa")
    List<Long> GetListCods();

    @Query("SELECT DataCriacao FROM Mapa")
    List<String> GetListDataCriacao();

    @Query("SELECT HoraCriacao FROM Mapa")
    List<String> GetListHoraCriacao();

    @Query("SELECT FotoMapaInicial FROM Mapa Where DataCriacao = :DATA AND HoraCriacao > :HORA_INICIAL  AND HoraCriacao < :HORA_FINAL ")
    public List<byte[]> GetFotoMapa(String DATA,String HORA_INICIAL,String HORA_FINAL);

}
