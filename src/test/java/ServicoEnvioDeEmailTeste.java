import com.mockito.exemplos.Email;
import com.mockito.exemplos.Formato;
import com.mockito.exemplos.PlataformaDeEnvio;
import com.mockito.exemplos.ServicoEnvioEmail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServicoEnvioDeEmailTeste {

    @Mock
    private PlataformaDeEnvio plataforma;

    @InjectMocks
    private ServicoEnvioEmail servico;

    @Captor
    ArgumentCaptor<Email> captor;

    @Test
    void valdiarDadosEnviadosParaPlataforma(){
        String enderecoDeEmail = "usuario@teste.com.br";
        String mensagem = "Aprendendo Captor Mockito";
        boolean ehFormatoHtml = false;

        servico.enviaEmail(enderecoDeEmail,mensagem,ehFormatoHtml);

        Mockito.verify(plataforma).enviaEmail(captor.capture());

        Email emailCapturado = captor.getValue();

        Assertions.assertEquals(enderecoDeEmail, emailCapturado.getEnderecoEmail());
        Assertions.assertEquals(mensagem, emailCapturado.getMensagem());
        Assertions.assertEquals(Formato.TEXTO, emailCapturado.getFormato());
    }

}
