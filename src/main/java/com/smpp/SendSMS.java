package com.smpp;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.AlertNotification;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.DataSm;
import org.jsmpp.bean.DeliverSm;
import org.jsmpp.bean.DeliveryReceipt;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.session.Session;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.InvalidDeliveryReceiptException;
import org.jsmpp.util.TimeFormatter;

public class SendSMS {
	private static TimeFormatter timeFormatter = new AbsoluteTimeFormatter();

	private static final String address = "FTTHCMS";

	private static final String SERVICE_TYPE = "CMT";

	public static void sendsms(String Number, String message, String REMARKS, String USER, String REF_TOKEN) {
		// bind(connect)
		SMPPSession session = SmsSession.getSession();
		session.setTransactionTimer(5000);
		// send Message

		String msglogid = null;
		String msglog = null;
		if (message.length() > 200) {
			msglog = message.substring(0, 200);
		} else
			msglog = message;

		SMSDao smsdao = new SMSDao();
		try {

			// taking only 200 character for message

			// set RegisteredDelivery
			final RegisteredDelivery registeredDelivery = new RegisteredDelivery();
			registeredDelivery.setSMSCDeliveryReceipt(SMSCDeliveryReceipt.SUCCESS_FAILURE);

			String messageId = session.submitShortMessage(SERVICE_TYPE, TypeOfNumber.ALPHANUMERIC,
					NumberingPlanIndicator.UNKNOWN, address, TypeOfNumber.NATIONAL, NumberingPlanIndicator.UNKNOWN,
					Number, new ESMClass(), (byte) 0, (byte) 1, timeFormatter.format(new Date()), null,
					registeredDelivery, (byte) 0, new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, null, false), (byte) 0,
					message.getBytes());
			msglogid = messageId;
			System.out.println("Message submitted, message_id is " + messageId);

		} catch (PDUException e) {
			// Invalid PDU parameter
			System.err.println("Invalid PDU parameter");
			e.printStackTrace();
		} catch (ResponseTimeoutException e) {
			// Response timeout
			System.err.println("Response timeout");
			e.printStackTrace();
		} catch (InvalidResponseException e) {
			// Invalid response
			System.err.println("Receive invalid respose");
			e.printStackTrace();
		} catch (NegativeResponseException e) {
			// Receiving negative response (non-zero command_status)
			System.err.println("Receive negative response");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO error occur");
			e.printStackTrace();
		}

		// Set listener to receive deliver_sm
		session.setMessageReceiverListener(new MessageReceiverListener() {

			public void onAcceptDeliverSm(DeliverSm deliverSm) throws ProcessRequestException {
				System.out.println("Testing inside listener");
				if (MessageType.SMSC_DEL_RECEIPT.containedIn(deliverSm.getEsmClass())) {
					// delivery receipt
					try {
						DeliveryReceipt delReceipt = deliverSm.getShortMessageAsDeliveryReceipt();
						long id = Long.parseLong(delReceipt.getId()) & 0xffffffff;
						String messageId = Long.toString(id, 16).toUpperCase();
						try {
							String msglog = null;
							if (message.length() > 200) {
								msglog = message.substring(0, 200);
							} else
								msglog = message;
							java.sql.Date submitDate = new java.sql.Date(delReceipt.getSubmitDate().getTime());
							java.sql.Date DoneDate = new java.sql.Date(delReceipt.getDoneDate().getTime());

							smsdao.UPDATESMSLOG(messageId, delReceipt.getId(), submitDate, DoneDate, msglog,
									delReceipt.getFinalStatus().toString(), REMARKS, USER, REF_TOKEN, Number);

						} catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println("received '" + messageId + "' : " + delReceipt);
					} catch (InvalidDeliveryReceiptException e) {
						System.err.println("receive failed");
						e.printStackTrace();
					}
				} else {
					// regular short message
					System.out.println("Receiving message : " + new String(deliverSm.getShortMessage()));
				}
			}

			public void onAcceptAlertNotification(AlertNotification alertNotification) {
				System.out.println("onAcceptAlertNotification");
			}

			public DataSmResult onAcceptDataSm(DataSm dataSm, Session source) throws ProcessRequestException {
				System.out.println("onAcceptDataSm");
				return null;
			}
		});
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				smsdao.ADDIFNOTEXISTSMSLOG(msglogid, null, null, null, msglog, null, REMARKS, USER, REF_TOKEN, Number);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.unbindAndClose();
		}
	}

	// return ("Send");

}