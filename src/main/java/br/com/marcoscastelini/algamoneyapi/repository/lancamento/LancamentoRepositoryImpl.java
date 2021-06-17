package br.com.marcoscastelini.algamoneyapi.repository.lancamento;

import br.com.marcoscastelini.algamoneyapi.model.Lancamento;
import br.com.marcoscastelini.algamoneyapi.model.QLancamento;
import br.com.marcoscastelini.algamoneyapi.model.tabelas.SLancamento;
import br.com.marcoscastelini.algamoneyapi.repository.filter.LancamentoFilter;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.SQLTemplates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;

public class LancamentoRepositoryImpl implements LancamentoRespositoryQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {
        QLancamento lancamento = QLancamento.lancamento;

        JPAQuery<Lancamento> query = new JPAQueryFactory(entityManager)
                .selectFrom(lancamento);

        query = aplicarFiltros(filter, query);
        query = aplicarPaginacao(pageable, query);

        return new PageImpl<>(query.fetch(), pageable, totalRegistros(filter));
    }

    private JPAQuery<Lancamento> aplicarPaginacao(Pageable pageable, JPAQuery<Lancamento> query) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query = query.limit(totalRegistrosPorPagina);
        query = query.offset(primeiroRegistroDaPagina);

        return query;
    }

    private JPAQuery<Lancamento> aplicarFiltros(LancamentoFilter filter, JPAQuery<Lancamento> query) {
        QLancamento lancamento = QLancamento.lancamento;

        if (filter.getDescricao() != null && !filter.getDescricao().isBlank()) {
            query = query.where(lancamento.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
        }

        if (filter.getDataVencimentoDe() != null) {
            query = query.where(lancamento.dataVencimento.goe(filter.getDataVencimentoDe()));
        }
        if (filter.getDataVencimentoAte() != null) {
            query = query.where(lancamento.dataVencimento.loe(filter.getDataVencimentoAte()));
        }

        return query;
    }

    private Long totalRegistros(LancamentoFilter filter) {
        SLancamento lancamento = SLancamento.lancamento;
        JPASQLQuery<Long> query = new JPASQLQuery<Long>(entityManager, SQLTemplates.DEFAULT)
                .select(lancamento.id.count())
                .from(lancamento);

        query = aplicarFiltrosNativos(filter, query);

        return query.fetchOne();
    }

    private JPASQLQuery<Long> aplicarFiltrosNativos(LancamentoFilter filter, JPASQLQuery<Long> query) {
        SLancamento lancamento = SLancamento.lancamento;

        if (filter.getDescricao() != null && !filter.getDescricao().isBlank()) {
            query = query.where(lancamento.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
        }
        if (filter.getDataVencimentoDe() != null) {
            query = query.where(lancamento.dataVencimento.goe(Date.valueOf(filter.getDataVencimentoDe())));
        }
        if (filter.getDataVencimentoAte() != null) {
            query = query.where(lancamento.dataVencimento.loe(Date.valueOf(filter.getDataVencimentoAte())));
        }
        return query;
    }
}
