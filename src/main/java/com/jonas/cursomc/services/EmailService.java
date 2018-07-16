package com.jonas.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.jonas.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);

}
