package com.transactions.resources;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.transactions.dao.TransactionsDao;
import com.transactions.dao.TransactionsDaoMemory;
import com.transactions.model.Transaction;
import com.transactions.model.ValidationException;

/**
 * The Class TransactionResource implements REST API calls to retrieve and operate with transactions.
 */
@Path("transactions")
public class TransactionResource {

	private static final Logger LOGGER = Logger.getLogger(TransactionResource.class.getName());

	/** The DAO to access transactions. */
	private TransactionsDao dao = TransactionsDaoMemory.getInstance();

	/**
	 * Validates and persists new transaction entity
	 *
	 * @param transaction the transaction to be logged
	 * @return successful status response; BAD_REQUEST response if transaction data is invalid; INTERNAL_SERVER_ERROR if execution fails;
	 */
	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(Transaction transaction) {
		LOGGER.info("Call to create transaction");
		try {
			transaction.validate();
			Status responseStatus = dao.create(transaction) ? Status.CREATED : Status.NO_CONTENT;
			return Response.status(responseStatus).build();
		} catch (ValidationException ex) {
			return Response.status(Status.BAD_REQUEST).entity(new ResourceStatus(ex.getMessage())).build();
		} catch (Exception ex) {
			LOGGER.severe(ex.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ResourceStatus(ex.getMessage())).build();
		}
	}
}
