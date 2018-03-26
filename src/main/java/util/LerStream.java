package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
/**
 * 
 * @author pfugazza
 *
 * <p>Ler arquivos-texto a partir do contexto da aplica��o (via <code>InputStream</code>) ou a partir
 * de um arquivo fora do contexto da aplica��o (via <code>FileReader</code>)
 */
public class LerStream 
{
	private	InputStream is = null;
	private FileReader fileMI = null;
	private BufferedReader br;
	private String linha = "{OK}";
	/**
	 * 
	 * @return <b>{OK}</b> se o arquivo est� pronto para leitura;<br>
	 * <b>{FileNotFoundException}</b> se o arquivo n�o foi encontrado;<br>
	 * <b>{null}</b> se o arquivo n�o puder ser lido do contexto da aplica��o.
	 */
	private String getLinha() 
	{
		return linha;
	}
	public boolean prontoParaLeitura()
	{
		return getLinha().equals("{OK}");
	}
	public boolean arquivoNaoEncontrado()
	{
		return getLinha().equals("{null}");
	}
	/**
	 * 
	 * @param fileName - nome do arquivo no formato u:\path\nomeDoArquivo.extens�o
	 * <p>Ler arquivo-texto fora do contexto da aplica��o (via <code>InputStream</code>)
	 * <p>Se arquivo n�o encontrado em <code>getLinha()</code> estar� o conte�do 
	 * "<b>{FileNotFoundException}</b>", caso contr�rio este conte�do sera "<b>{OK}</b>".
	 */
	public LerStream(String fileName)
	{
		try 
		{
			fileMI = new FileReader(fileName);
			br = new BufferedReader(fileMI);
		} 
		catch (FileNotFoundException e) 
		{
			linha = "{FileNotFoundException}";    // arquivo n�o encontrado
		}
	}
	/**
	 * 
	 * @return conte�do da linha lida. Se nada mais existir para ser lido, <i>proximaLinha()</i> 
	 * retornar� o string <b>{EOF}</b> e, neste caso, executar� o m�todo <i>close()</i>. 
	 */
	public String proximaLinha()
	{
		try 
		{
			linha = br.readLine();
			if ( linha == null )
			{
				linha = "{EOF}";                  // fim de arquivo
				close();
			}
			else if ( linha.startsWith("*"))      // coment�rio
				linha = proximaLinha();
		} 
		catch (IOException e) 
		{
			linha = "{IOException}";              // fim de arquivo
			e.printStackTrace();
		}
		return linha;
	}
	/**
	 * M�todo para fechar o <i>BufferedReader</i> e o <i>FileReader</i><P>
	 * Obs. cham�-lo se n�o deixar ir at� a leitura da �ltima linha do arquivo, ou seja,
	 * se <i>proximaLinha()</i> n�o retornar <b>{EOF}</b>.
	 */
	public void close() 
	{
		try
		{
			br.close();
			if ( fileMI != null )
				fileMI.close();
			if ( is != null)
				is.close();
		}
		catch (IOException e) 
		{
			linha = "{IOException}";              // fim de arquivo
		}
		catch (NullPointerException npe)
		{
			linha = "{NullPointerException}";
		}

	}
	/**
	 * Ir� retornar a primeira linha que iniciar pelo caracter <b>[</b>. Geralmente lida do arquivo <b>MensagemGlobal.Status</b>.</br>
	 * Ir� preencher, tamb�m, o <i>ArrayList</i> <i>cores</i> com os c�digos de cores que ser�o usadas para exibir o item de menu
	 * <b>Mensagens</b>. Estes c�digos est�o caracterizados por ter o caracter <b>#</b> no in�cio de cada linha. Se nenhuma cor
	 * for fornecida, assumir� uma default.
	 * @return
	 */
	public String getMensagem(ArrayList<String> cores) 
	{
		String msgMI = "[?]Nada tenho a lhe dizer neste momento, ok?";
		if (prontoParaLeitura())
		{
			while ( ( (linha = proximaLinha()) != "{EOF}" ) && (msgMI.startsWith("[?]")) )
			{
				if ( linha.startsWith("[") )
					msgMI = linha;
				else if ( linha.startsWith("#") )
					cores.add(linha);
			}
		}
		close();
		if (cores.size() == 0)
			cores.add("#ff6820");
		
		return msgMI;
	}
	
}

