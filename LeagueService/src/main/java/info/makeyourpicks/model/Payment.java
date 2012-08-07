package info.makeyourpicks.model;

import java.text.SimpleDateFormat;

import com.delesio.model.AbstractPersistantObject;
import com.delesio.model.IPersistable;

public class Payment extends AbstractPersistantObject {

	private String transactionId;
	private String transactionDate;
	private double amountDue;
	private League league;
	private String token;
	private String payerId;
	private double amountPaid;
	private String paymentType;
	private String currencyCode;
	private double feeAmount;
	private double seatleAmount;
	private double taxAmount;
	private String exchangeRate;
	private String paymentStatus;
	private String pendingReason;
	private String reasonCode;
	@Override
	public IPersistable createTestObject() {
		Payment payment = new Payment();
		payment.setTransactionId("123456789");
		//payment.setTransactionDate(new Timestamp(System.currentTimeMillis()));
		payment.setAmountDue(100);
		payment.setAmountPaid(50);
		return payment;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
		
	}
	
	public String getAmountDisplay()
	{
		return "$"+String.valueOf(amountDue);
	}
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	public String getTransactionDateDisplay()
	{
		return simpleDateFormat.format(transactionDate);
	}

	public String getItem_name()
	{
		return league.getLeagueName();
	}
	
	public String getFormatedAmount()
	{
		return String.valueOf(amountDue)+"0";
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public String getPayerId()
	{
		return payerId;
	}

	public void setPayerId(String payerId)
	{
		this.payerId = payerId;
	}

	public double getAmountDue()
	{
		return amountDue;
	}

	public void setAmountDue(double amountDue)
	{
		this.amountDue = amountDue;
	}

	public double getAmountPaid()
	{
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid)
	{
		this.amountPaid = amountPaid;
	}

	public String getPaymentType()
	{
		return paymentType;
	}

	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
	}

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}

	public double getFeeAmount()
	{
		return feeAmount;
	}

	public void setFeeAmount(double feeAmount)
	{
		this.feeAmount = feeAmount;
	}

	public double getSeatleAmount()
	{
		return seatleAmount;
	}

	public void setSeatleAmount(double seatleAmount)
	{
		this.seatleAmount = seatleAmount;
	}

	public double getTaxAmount()
	{
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount)
	{
		this.taxAmount = taxAmount;
	}

	public String getExchangeRate()
	{
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate)
	{
		this.exchangeRate = exchangeRate;
	}

	public SimpleDateFormat getSimpleDateFormat()
	{
		return simpleDateFormat;
	}

	public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat)
	{
		this.simpleDateFormat = simpleDateFormat;
	}

	public void setTransactionDate(String transactionDate)
	{
		this.transactionDate = transactionDate;
	}

	public String getPaymentStatus()
	{
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus)
	{
		this.paymentStatus = paymentStatus;
	}

	public String getPendingReason()
	{
		return pendingReason;
	}

	public void setPendingReason(String pendingReason)
	{
		this.pendingReason = pendingReason;
	}

	public String getReasonCode()
	{
		return reasonCode;
	}

	public void setReasonCode(String reasonCode)
	{
		this.reasonCode = reasonCode;
	}

	public String getTransactionDate()
	{
		return transactionDate;
	}

	
	
}
