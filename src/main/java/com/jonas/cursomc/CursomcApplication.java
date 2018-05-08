package com.jonas.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jonas.cursomc.domain.Categoria;
import com.jonas.cursomc.domain.Cidade;
import com.jonas.cursomc.domain.Cliente;
import com.jonas.cursomc.domain.Endereco;
import com.jonas.cursomc.domain.Estado;
import com.jonas.cursomc.domain.Produto;
import com.jonas.cursomc.domain.enuns.TipoCliente;
import com.jonas.cursomc.repositories.CategoriaRepository;
import com.jonas.cursomc.repositories.CidadeRepository;
import com.jonas.cursomc.repositories.ClienteRepository;
import com.jonas.cursomc.repositories.EnderecoRepository;
import com.jonas.cursomc.repositories.EstadoRepository;
import com.jonas.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoReposiroty;
	
	@Autowired
	private CidadeRepository cidadeReposiroty;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressoa", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoReposiroty.saveAll(Arrays.asList(est1, est2));
		
		cidadeReposiroty.saveAll(Arrays.asList(c1, c2, c3));
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "82628386372", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("32327698", "986781108"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "277", "apto 203", "itaperi", "60735350", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Recanto Tranquilo", "300", "apto 207", "centro", "60714350", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		
		
	}
}
