package com.transactions.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.transactions.dao.TransactionsDao;
import com.transactions.dao.TransactionsDaoMemory;
import com.transactions.model.Statistic;

/**
 * The Class StatisticsResource implements REST API calls to retrieve
 * transactions statistic.
 */
@Path("statistics")
public class StatisticsResource {

	/** The DAO to access transactions. */
	private TransactionsDao dao = TransactionsDaoMemory.getInstance();

	/**
	 * Processes requests to load past minute statistics.
	 *
	 * @param request the API request
	 * @return Response with list of contacts as an entity and Links header with
	 *         pagination links, returns OK response wrapping statistics entity
	 */
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPastMinute() {
		Statistic result = dao.getMinuteStatistic();
		return Response.ok().entity(result).build();
	}
}
