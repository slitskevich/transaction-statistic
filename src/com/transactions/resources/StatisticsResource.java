package com.transactions.resources;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.transactions.dao.TransactionsDao;
import com.transactions.dao.TransactionsDaoMemory;
import com.transactions.model.Statistic;
import com.transactions.model.Transaction;

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
		Statistic result = buildStatistic((new Date()).getTime() - 60 * 1000);
		return Response.ok().entity(result).build();
	}

	/**
	 * Builds statistic for the period specified by the period start timestamp
	 * 
	 * @param periodStart period start timestamp
	 * @return statistic for the period
	 */
	private Statistic buildStatistic(long periodStart) {
		Statistic result = new Statistic();
		List<Transaction> transactions = dao.loadForPeriod(periodStart);
		if (transactions.size() > 0) {
			double sum = 0.0;
			double min = Double.MAX_VALUE;
			double max = Double.MIN_VALUE;
			for (Transaction next : transactions) {
				sum += next.getAmount();
				if (min > next.getAmount()) {
					min = next.getAmount();
				}
				if (max < next.getAmount()) {
					max = next.getAmount();
				}
			}
			result.setCount(transactions.size());
			result.setSum(sum);
			result.setAverage(sum / transactions.size());
			result.setMax(max);
			result.setMin(min);
		}
		return result;
	}
}
