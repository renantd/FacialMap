package br.sofex.com.facialmap.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.sofex.com.facialmap.Json.JsonCep;
import br.sofex.com.facialmap.R;

public class Validacoes {

    Context mContext;
    public Validacoes(Context context){
        this.mContext = context;
    }
    private AlertDialog alerta;

    //Todo: Validar Cadastro Paciente - Part 1 = (Dados Pessoais) ok
    public boolean ValidarDadosPessoais(String Nome , String Documento , String DocumentoValor,  String Nacionalidade , String Etnia ,String DataDeNascimento, String SexoDoPaciente ){

        if(!Nome.isEmpty() && Documento  != null && DocumentoValor  != null && Nacionalidade != "Escolha um País"  && Etnia != "Escolha a Etnia" && !DataDeNascimento.isEmpty() && SexoDoPaciente  != ""){
            // Date to pt br
            Date d = new Date();
            DateFormat df2 = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("pt", "BR"));
            String Sexo = null;
            if(SexoDoPaciente.equals("Masculino")){
                Sexo = SexoDoPaciente;
            }else{
                Sexo = SexoDoPaciente;
            }
            if(Nome.length() > 2 && Documento != "Documento" && Documento_isValid(DocumentoValor) && Nacionalidade != "Escolha um País"  && Etnia != "Escolha a Etnia" && check_isValidDate(DataDeNascimento) && Sexo != null ){
                return true;
            } else{
                if(Nome.length() <= 2){ErrorMsg("Nome do paciente é muito curto !");}
                else if(Documento == "Documento"){ErrorMsg("Tipo de Documento Inválido !");}
                else if(Documento_isValid(DocumentoValor) == false){ErrorMsg("Valor do documento é Inválido !");}
                else if(Nacionalidade == "Escolha um País"){ErrorMsg("Nacionalidade Inválido !");}
                else if(Etnia == "Escolha a Etnia"){ErrorMsg("Etnia Inválida !");}
                else if(check_isValidDate(DataDeNascimento) == false){/*ErrorMsg("A data de nascimento é inválida");*/}
                else if(Sexo == null ){ErrorMsg("Tipo de Sexo Inválido");}
              return false;
            }
        }else{
            if(Nome.isEmpty()){
                ErrorMsg("Nome do paciente em branco !");
            }
            else if(Documento == null){
                ErrorMsg("Tipo de Documento em branco !");
            }
            else if(DocumentoValor == null){
                ErrorMsg("Valor do documento em branco !");
            }
            else if(Nacionalidade == "Escolha um País"){
                ErrorMsg("Nacionalidade em branco !");
            }
            else if(Etnia == "Escolha a Etnia"){
                ErrorMsg("Etnia Inválida em branco ");
            }
            else if(DataDeNascimento.isEmpty()){
                ErrorMsg("Data de Nascimento em branco ");
            }
            else if(SexoDoPaciente == ""){
                ErrorMsg("Tipo de Sexo em branco ");
            }
            return false;
        }
       /* // Date to pt br
        Date d = new Date();
        DateFormat df2 = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("pt", "BR"));

        String Sexo = null;
        if(SexoDoPaciente.equals("Masculino")){
          Sexo = SexoDoPaciente;
        }else{
          Sexo = SexoDoPaciente;
        }
        if(Nome.length() > 2 && Documento != "Documento" && Documento_isValid(DocumentoValor) && Nacionalidade != "Escolha um País"  && Etnia != "Escolha a Etnia" && check_isValidDate(DataDeNascimento) && Sexo != null ){
            //SucessMsg("Part 1 OK");
            return true;
        } else{
            if(Nome.length() <= 2){
               ErrorMsg("Nome do paciente é muito curto !");
            }
            else if(Documento == "Documento"){
                ErrorMsg("Tipo de Documento Inválido !");
            }
            else if(Documento_isValid(DocumentoValor) == false){
                ErrorMsg("Valor do documento é Inválido !");
            }
            else if(Nacionalidade == "Escolha um País"){
                ErrorMsg("Nacionalidade Inválido !");
            }
            else if(Etnia == "Escolha a Etnia"){
                ErrorMsg("Etnia Inválida !");
            }
            else if(check_isValidDate(DataDeNascimento) == false){
                ErrorMsg("A data de nascimento é inválida");
            }
            else if(Sexo == null ){
                ErrorMsg("Tipo de Sexo Inválido");
            }
          return false;
        }*/
    }

    //Todo: Validar Cadastro Paciente - Part 2 = (Dados de Contato) ok
    public boolean ValidarDadosDeContato(String TelefoneFixo , String TelefoneCelular , String Email ){

        if(!TelefoneFixo.isEmpty() && !TelefoneCelular.isEmpty() && !Email.isEmpty()) {
            if ( checkTelFixo(TelefoneFixo) && checkTelCelular(TelefoneCelular)&& Email_isValid(Email)) {
              return true;
           }else{
              if(checkTelFixo(TelefoneFixo) == false){
                 ErrorMsg(" O Número do Telefone Fixo digitado é inválido");
              }else if(checkTelCelular(TelefoneCelular) == false){
                 ErrorMsg(" O Número do Telefone Celular digitado é inválido");
              }
              else if(DDDisValid(TelefoneFixo) == false){
                 ErrorMsg(" O DDD do Telefone Fixo é inválido\n DDD de telefones são maiores que 10");
              }else if(DDDisValid(TelefoneCelular) == false){
                 ErrorMsg(" O DDD do Telefone Celular é inválido\n DDD de telefones são maiores que 10");
              }
              else if(Email_isValid(Email) == false){
                 ErrorMsg(" O Email Digitado é inválido !");
              }
                return false;
            }
        }else{
            if(TelefoneFixo.isEmpty()){
              ErrorMsg(" Telefone Fixo em branco !");
                return false;
            }
            if(TelefoneCelular.isEmpty()){
                ErrorMsg(" Telefone Celular em branco !");
                return false;
            }
            if(Email.isEmpty()){
                ErrorMsg(" E-mail em branco !");
                return false;
            }
            return false;
        }

    }

    //Todo: Validar Cadastro Endereço - Part 3 = (Dados de Endereço) ok
    public boolean ValidarDadosEndereco(String Cep , String Endereco , String Complemento ,String  Numero,String Bairro,String  Municipio,String Estado ){

        String[] EstadoExtenco = new String[]{"Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal","Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará",
        "Paraíba","Paraná","Pernambuco","Piauí","Rio de Janeiro","Rio Grande do Norte","Rio Grande do Sul","Rondônia","Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins"};

        String[] EstadoSigla = new String[]{"AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA",
                "PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};

      if(!Cep.isEmpty() && !Endereco.isEmpty() && !Complemento.isEmpty() && !Numero.isEmpty() && !Bairro.isEmpty() && !Municipio.isEmpty() && !Estado.isEmpty()){
            if(Cep_isValid(Cep)){
                String str2 = Cep.replace("-", "");
                try {
                  String retorno = new JsonCep(mContext,str2).execute().get();
                  if(retorno != null){
                     if(Cep_CheckReturn(retorno) == true){
                         Log.e("App1"," - "+JsonGetLogradouro(retorno)+" "+JsonGetBairro(retorno)+" "+JsonGetCidade(retorno)+" "+JsonGetEstado(retorno));
                         Log.e("App1"," - "+Endereco+" "+Bairro+" "+Municipio+" "+Estado);
                         if(JsonGetLogradouro(retorno).toLowerCase().contains(Endereco.toLowerCase()) &&
                             JsonGetBairro(retorno).toLowerCase().contains(Bairro.toLowerCase()) &&
                             JsonGetCidade(retorno).toLowerCase().contains(Municipio.toLowerCase()) &&
                             JsonGetEstado(retorno).toLowerCase().contains(Estado.toLowerCase())){
                             return true;
                         } else{
                             ErrorMsg(" O(s) dado(s) informado(s)\nnão confere(m) com , os dados do CEP\n nos correios");
                             return false;
                         }
                     }else{
                         ErrorMsg(" O Cep informado é inválido");
                         return false;
                     }
                  }else{
                     return false;
                  }
                } catch (InterruptedException e) {
                    ErrorMsg("InterruptedException :"+e);
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    ErrorMsg("ExecutionException :"+e);
                    e.printStackTrace();
                }
            }else{ ErrorMsg(" O Cep digitado é inválido !"); return false;}
         return true;
      }else{
          if(Cep.isEmpty()) {ErrorMsg("O Cep digitado está em Branco !");}
          else if(Endereco.isEmpty()) {ErrorMsg("O Endereço está em Branco !");}
          else if(Complemento.isEmpty()) {ErrorMsg("O Complemento está em Branco !");}
          else if(Numero.isEmpty()) {ErrorMsg("O Número está em Branco !");}
          else if(Bairro.isEmpty()) {ErrorMsg("O Bairro está em Branco !");}
          else if(Municipio.isEmpty()) {ErrorMsg("O Município está em Branco !");}
          else if(Estado.isEmpty()) {ErrorMsg("O Estado está em Branco !");}
        return false;
      }
    }

    //Todo: Validar Cadastro Endereço - Part 4 = (Imagem Perfil) ok
    public boolean ValidarDadosDePerfil(String CaminhoDaFoto , byte[] Foto , String NomeDaFoto){
      if(CaminhoDaFoto!= null && Foto != null && NomeDaFoto != null) {
         return true;
      } else{
          if(CaminhoDaFoto == null) {
              ErrorMsg("Local da foto não existe!");
              return false;
          }
          else if(Foto == null) {
              ErrorMsg("Nenhuma foto selecionada!");
              return false;
          }
          else if(NomeDaFoto == null) {
              ErrorMsg("Nenhuma foto selecionada\nNome da foto em branco !");
              return false;
          }
        return false;
      }
    }

    //Todo: Validar o Cadastro antes de salvar
    public boolean ValidarRegistro(boolean part1,boolean part2,boolean part3,boolean part4){
       if(part1 == true && part2 == true && part3 == true  && part4 == true){return true;
       } else{return  false;}
    }

    //Todo: Validar o Update antes de salvar
    public boolean ValidarRegistroUpdate(boolean part1,boolean part2,boolean part3){
        if(part1 == true && part2 == true && part3 == true){return true;
        } else{return  false;}
    }
    public void Teste(){
        ErrorMsg("Teste");
    }


    //Todo: Validar o Dados na Alteração

          //Todo: Validar o nomedo paciente
          public boolean Nome_isValid(String Nome ){
                if(Nome.isEmpty()){
                   ErrorMsg("Nome do Paciente em Branco !");
                   return false;
                }else{
                    if(Nome.length() > 2 ){
                        return true;
                    } else{
                        ErrorMsg("Nome do Paciente é muito curto !");
                        return false;
                    }
                }

            }

          //Todo: Validar o documento
          public  boolean Documento_isValid(String DocumentoValor){
                String regeCPF = "^[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}$";
                String regeCTPS = "^[0-9]{7}$";
                String regeCNH = "^[0-9]{12}$";
                String regeTituloEleitor ="^[0-9]{4}.[0-9]{4}.[0-9]{4}$";
                String regePassaporte ="^[a-zA-Z]{2}[0-9]{6}$";
                return DocumentoValor.matches(regeCPF) ||
                        DocumentoValor.matches(regeCTPS) ||
                        DocumentoValor.matches(regeCNH) ||
                        DocumentoValor.matches(regeTituloEleitor) ||
                        DocumentoValor != null || DocumentoValor != "" ||
                        DocumentoValor.matches(regePassaporte);
            }

          //Todo: Validar o documento
          public  boolean Nacionalidade_isValid(String Nascionalidade){
              if(Nascionalidade != "Escolha um País" || Nascionalidade != ""){
                  return true;
              }else{
                  ErrorMsg("A Nascionalidade escolhida é inválida !");
                  return false;
              }

          }

          //Todo: Validar o Etinia
          public  boolean Etinia_isValid(String Etinia){
                if(Etinia != "Escolha a Etnia" || Etinia != ""){
                    return true;
                }else{
                    ErrorMsg("A Etnia escolhida é inválida !");
                    return false;
                }

            }

          //TODO : Validação de Data
          public boolean check_isValidDate(String DataNascimento){
            //String Data2 = Data.replaceAll("/", "");
            if (DataNascimento.length() == 10) {
                String Data2 = DataNascimento.replaceAll("/", "");

                Integer dia = Integer.valueOf(Data2.substring(0, 2));
                Integer mes = Integer.valueOf(Data2.substring(2, 4));
                Integer ano = Integer.valueOf(Data2.substring(4, 8));

                try {
                    SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
                    Date dateToday = new Date(System.currentTimeMillis());

                    String b = formatter.format(dateToday);
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                    Date dataX = new Date(format.parse(DataNascimento).getTime());
                    Date dataY = new Date(format.parse(b).getTime());
                    //Log.e("App 1 ","Data1: " + dataX + " Data2: " + dataY);
                    if (dataX.after(dataY)) {

                        //ErrorMsg("Não é possivel inserir \n a data de nascimento \n como data futura");
                        return false;

                    } else if (dataX.before(dataY)) {
                        Log.e("App 1 ","Data: " + DataNascimento + " é inferior à " + b);
                        Date dataNascimento = null;
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        df.setLenient(false);
                        dataNascimento = df.parse(DataNascimento);
                        if(calcularIdade(dataNascimento) < 18){
                            ErrorMsg("Menor de Idade");
                            return false;

                        }else{return true;}

                    } else{
                        Log.e("App 1 ","Data: " + DataNascimento + " é igual à " + b);
                        //ErrorMsg("A data de nascimento \n não pode ser igual \n a data de Hoje");
                        return false;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return true;
            }else{
                return false;
            }
        }
          public static Integer calcularIdade(Date data) {
            Integer anos = 0;
            Integer meses = 0;
            Integer dias = 0;

            Calendar dataAtual;
            Calendar dataNascimento;

            dataAtual = Calendar.getInstance();
            dataNascimento = Calendar.getInstance();
            dataNascimento.setTime(data);

            anos = anos + (dataAtual.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR));
            meses = meses + (dataAtual.get(Calendar.MONTH) - dataNascimento.get(Calendar.MONTH));
            dias = dias + (dataAtual.get(Calendar.DAY_OF_MONTH) - dataNascimento.get(Calendar.DAY_OF_MONTH));

            if (dataAtual.get(Calendar.MONTH) == dataNascimento.get(Calendar.MONTH)) {
                if (dataAtual.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
                    dias = 30 + dias;
                    meses = 12 + meses;
                    anos = anos - 1;
                }
            } else if (dataAtual.get(Calendar.MONTH) < dataNascimento.get(Calendar.MONTH)) {
                if (dataAtual.get(Calendar.DAY_OF_MONTH) >= dataNascimento.get(Calendar.DAY_OF_MONTH)) {
                    meses = 12 + meses;
                    anos = anos - 1;
                } else if (dataAtual.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
                    dias = 30 + dias;
                    meses = 12 + meses;
                    anos = anos - 1;
                }
            } else if (dataAtual.get(Calendar.MONTH) > dataNascimento.get(Calendar.MONTH)) {
                if (dataAtual.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
                    dias = 30 + dias;
                    meses = meses - 1;
                }
            }

            return anos;
        }

          //TODO : Validação de Sexo
          public boolean check_isValidSexo(String Sexo){
              if(Sexo != ""){
                  return true;
              }else{
                  ErrorMsg("Nome do Paciente é muito curto !");
                  return false;
              }
        }

          //Todo: Validar o telefone
          public static boolean checkTelCelular(String Celular){
                String RegexTelCelular = "\\([1-9]{2}\\) [9][0-9]{4}-[0-9]{4}|\\(\\d{2}\\)[9][0-9]{4}-[0-9]{4}";
                if(Celular.length() == 15){
                    if(Celular.matches(RegexTelCelular) || Celular.matches(RegexTelCelular)){
                        return true;
                    }else{return false;}
                }else{return false;}

            }
          public static boolean checkTelFixo(String Fixo){
                String RegexTelFixo = "\\([1-9]{2}\\) [3][0-9]{3}-[0-9]{4}|\\(\\d{2}\\)[3][0-9]{3}-[0-9]{4}";
                String RegexTelFixoNulo = "\\([0]{2}\\) [0]{4}-[0]{4}|\\([0]{2}\\)[0]{4}-[0]{4}|\\([0]{2}\\)[0]{5}-[0]{4}|\\([0]{2}\\) [0]{5}-[0]{4}";
                if(Fixo.length() == 14){
                    if(Fixo.matches(RegexTelFixo) || Fixo.matches(RegexTelFixoNulo)){
                        Log.e("App1","Fixo "+Fixo.length());
                        return true;
                    }else{return false;}
                }else {
                    if (Fixo.length() == 15 && Fixo.matches(RegexTelFixoNulo)) {
                        Log.e("App1","Fixo "+Fixo.length());
                        return true;
                    } else {
                        return false;
                    }
                }
            }
          public static boolean DDDisValid(String DDD){
                String Aux = "";
                for(int i = 0; i < DDD.length();i++){
                    char c = DDD.charAt(i);
                    if(i >= 1 && i < 3){
                        Aux = Aux + c;
                    }
                }
                if(Integer.parseInt(Aux) < 11 ){
                    return false;
                }else{
                    return true;
                }
            }

          //Todo: Validar o E-mail
           public boolean Email_isValid(String email) {
            String RegexEmail = "[A-z0-9!@#$%¨&*]+[@][gmail|outlook|live]+\\.[A-z]{3}|[A-z0-9!@#$%¨&*]+[@][yahoo]+\\.[A-z]{3}\\.[A-z]{2}";
            if(email.matches(RegexEmail)){
                return true;
            } else{ return  false;}
            /*String email2 = email+"/";
            Boolean b = false;
            if(email2.contains("@")){
                String EmailAux = email2.substring(email2.indexOf("@"), email2.indexOf("/"));
                if( EmailAux.equalsIgnoreCase("@gmail.com") || EmailAux.equalsIgnoreCase("@outlook.com") || EmailAux.equalsIgnoreCase("@live.com") || EmailAux.equalsIgnoreCase("@yahoo.com.br")){
                    b = true;
                    Log.e("App1","Log : "+b+" - "+ EmailAux.equalsIgnoreCase("@gmail.com.br")+" "+EmailAux.equalsIgnoreCase("@outlook.com"+" "+EmailAux.equalsIgnoreCase("@live.com")+" "+EmailAux.equalsIgnoreCase("@yahoo.com.br")));

                }else{ErrorMsg("Email inválido"); b = false; Log.e("App1","Log B : "+ b);}
            } else{  ErrorMsg("Email inválido"); b = false;  Log.e("App1","Log B : "+ b);}
            return b;*/
        }
          //TODO : Validação de CEP
          public static boolean Cep_CheckReturn(String cepJsonXlm) {
            return !Character.isDigit(cepJsonXlm.charAt(12));
        }
          public static boolean Cep_isValid(String cep) {
            String CepAux = cep.replaceAll("-", "");
            String pattern = "[0-9]{5}[0-9]{3}";

            Pattern regexPattern = Pattern.compile(pattern);
            Matcher matcher = regexPattern.matcher(CepAux);
            boolean isMatch = matcher.find();
            boolean b = isMatch == true;
            return b;

        }

    //Todo: --------------- //


    private boolean isValidPassaporte(String Passaporte){
        String result = "";
        for(int i=0; i < Passaporte.length();i++){
            char c = Passaporte.charAt(i);
            if(i <= 1 && Character.isAlphabetic(c)){
                result = result + c;
            }
            if(i >= 2 && Character.isDigit(c)){
                result = result + c;
            }
        }
        if(result.length() < 8 || result.length() > 8){
            return false;
        }else{
            return true;
        }
    }

    //TODO: Desativado
    public String EstadoToSigla(String Estado){

        String[] EstadoExtenco = new String[]{"Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal","Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará",
                "Paraíba","Paraná","Pernambuco","Piauí","Rio de Janeiro","Rio Grande do Norte","Rio Grande do Sul","Rondônia","Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins"};

        String[] EstadoSigla = new String[]{"AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA",
                "PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
        String Sigla = "";
        switch(Estado) {
            case "Acre":
                Sigla = "AC";
                break;
            case "Alagoas":
                Sigla = "AC";
                break;
            case "Amapá":
                Sigla = "AC";
                break;
            case "Amazonas":
                Sigla = "AC";
                break;
            case "Bahia":
                Sigla = "AC";
                break;
            case "Ceará":
                Sigla = "AC";
                break;
            case "Distrito Federal":
                Sigla = "AC";
                break;
            case "Espírito Santo":
                Sigla = "AC";
                break;
            case "Goiás":
                Sigla = "AC";
                break;
            case "Maranhão":
                Sigla = "AC";
                break;
            case "Mato Grosso":
                Sigla = "AC";
                break;
            case "Mato Grosso do Sul":
                Sigla = "AC";
                break;
            case "Minas Gerais":
                Sigla = "AC";
                break;
            case "Pará":
                Sigla = "AC";
                break;
            case "Paraíba":
                Sigla = "AC";
                break;
            case "Pernambuco":
                Sigla = "AC";
                break;
            case "Piauí":
                Sigla = "AC";
                break;
            case "Rio de Janeiro":
                Sigla = "AC";
                break;
            case "Rio Grande do Norte":
                Sigla = "AC";
                break;
            case "Rio Grande do Sul":
                Sigla = "AC";
                break;
            case "Rondônia":
                Sigla = "AC";
                break;
            case "Roraima":
                Sigla = "AC";
                break;
            case "Santa Catarina":
                Sigla = "AC";
                break;
            case "São Paulo":
                Sigla = "AC";
                break;
            case "Sergipe":
                Sigla = "AC";
                break;
            case "Tocantins":
                Sigla = "AC";
                break;
        }
        return null;
    }


    //TODO Mensagens
    public  void ErrorMsg(String ErroMsg){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(mContext);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(mContext);

        //inflamos o layout alerta.xml na view
        View view = factory.inflate(R.layout.error, null);
        //definimos para o botão do layout um clickListener

        TextView textdialog = view.findViewById(R.id.tv_error);
        textdialog.setText(ErroMsg);
        textdialog.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textdialog.setTextSize(16);
        textdialog.setTextColor(Color.BLACK);

        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alerta.dismiss();
            }
        });


        alertadd.setView(view);
        TextView title = new TextView(mContext);
        // You Can Customise your Title here
        title.setText("Mapa Facial - Mensagem \t");

        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(15, 15, 15, 15);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(18);
        alertadd.setCustomTitle(title);

        alerta = alertadd.create();
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#23A4CA"));
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }
    public void SucessMsg(String Msg){

        AlertDialog.Builder alertadd = new AlertDialog.Builder(mContext);
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater factory = LayoutInflater.from(mContext);

        //inflamos o layout alerta.xml na view
        View view = factory.inflate(R.layout.sucess, null);
        //definimos para o botão do layout um clickListener

        TextView textdialog = view.findViewById(R.id.tv_sucess);
        textdialog.setText(Msg);
        textdialog.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textdialog.setTextSize(16);
        textdialog.setTextColor(Color.BLACK);

        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alerta.dismiss();
            }
        });


        alertadd.setView(view);
        TextView title = new TextView(mContext);
        // You Can Customise your Title here
        title.setText("Mapa Facial - Mensagem \t");

        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(15, 15, 15, 15);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(18);
        alertadd.setCustomTitle(title);

        alerta = alertadd.create();
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#23A4CA"));
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }


    //TODO: Json
    public static  String JsonGetLogradouro(String cep) {
        String LogradouroAux = cep.replaceAll("<logradouro>", "");
        String Logradouro = LogradouroAux.substring(0, LogradouroAux.indexOf("</logradouro>\n"));
        return Logradouro;
    }
    public static  String JsonGetBairro(String cep) {
        String BairroAux =  cep.substring(cep.indexOf("<bairro>") + 8);
        String Bairro = BairroAux.substring(0, BairroAux.indexOf("</bairro>\n"));
        return Bairro;
    }
    public static  String JsonGetCidade(String cep) {
        String CidadeAux =  cep.substring(cep.indexOf("<cidade>") + 8);
        String Cidade = CidadeAux.substring(0, CidadeAux.indexOf("</cidade>\n"));
        return Cidade;
    }
    public static  String JsonGetEstado(String cep) {
        String EstadoAux =  cep.substring(cep.indexOf("<estado>") + 8);
        String Estado = EstadoAux.substring(0, EstadoAux.indexOf("</estado>\n"));
        return Estado;
    }
}
