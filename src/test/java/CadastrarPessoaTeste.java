
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mockito.exemplos.ApiDosCorreios;
import com.mockito.exemplos.CadastrarPessoa;
import com.mockito.exemplos.DadosLocalizacao;
import com.mockito.exemplos.Pessoa;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;


@ExtendWith(MockitoExtension.class)
public class CadastrarPessoaTeste {

    @Mock
    private ApiDosCorreios apiDosCorreios;

    @InjectMocks
    private CadastrarPessoa cadastrarPessoa;

    @Test
    void validarDadosDeCadastro(){

       DadosLocalizacao dadosLocalizacao= new DadosLocalizacao("SP","Aracatuba","Avenida Principal","Casa","Centro");

        Mockito.when(apiDosCorreios.buscaDadosComBaseNoCep("16200000")).thenReturn(dadosLocalizacao);

        Pessoa vinicius = cadastrarPessoa.cadastrarPessoa("Vinicius","726777589", LocalDate.now(),"16200000");

        assertEquals ("Vinicius",vinicius.getNome());
        assertEquals ("726777589",vinicius.getDocumento());
        assertEquals ("SP",vinicius.getEndereco().getUf());
        assertEquals ("Casa",vinicius.getEndereco().getComplemento());

    }

    @Test
    void validarDadosDeCadastroExceptions(){

        DadosLocalizacao dadosLocalizacao= new DadosLocalizacao("SP","Aracatuba","Avenida Principal","Casa","Centro");

        Mockito.when(apiDosCorreios.buscaDadosComBaseNoCep(anyString())).thenThrow(IllegalArgumentException.class);

        Pessoa vinicius = cadastrarPessoa.cadastrarPessoa("Vinicius","726777589", LocalDate.now(),"16200000");

        assertEquals ("Vinicius",vinicius.getNome());
        assertEquals ("726777589",vinicius.getDocumento());
        assertEquals ("SP",vinicius.getEndereco().getUf());
        assertEquals ("Casa",vinicius.getEndereco().getComplemento());
    }

    @Test
    void lancaExceptionQuandoChmarApiDosCorreios(){
        Mockito.doThrow(IllegalArgumentException.class).when(apiDosCorreios).buscaDadosComBaseNoCep(anyString());

        Assertions.assertThrows(IllegalArgumentException.class, () -> cadastrarPessoa.cadastrarPessoa("Vinicius","726777589", LocalDate.now(),"16200000"));
    }

}
