package com.jonas.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jonas.cursomc.domain.Categoria;
import com.jonas.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	
	/*@Query("select distinct obj from Produto obj inner join obj.categorias cat where obj.nome like %:nome% AND cat IN :categorias")
	Page<Produto> search(@Param("nome")String nome, @Param("categorias")List<Categoria> categorias, Pageable pageRequest);*/
	
	@Transactional(readOnly=true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
	
	
}
