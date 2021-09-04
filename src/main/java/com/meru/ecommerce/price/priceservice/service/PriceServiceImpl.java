package com.meru.ecommerce.price.priceservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meru.ecommerce.price.priceservice.dao.PriceRepository;
import com.meru.ecommerce.price.priceservice.entity.Price;

@Service("PriceService")
public class PriceServiceImpl implements PriceService {

	@Autowired
	PriceRepository pr;

	@Override
	public List<Price> getAllProductsPrice() {
		return pr.findAll();
	}

	@Override
	public Price getPriceById(int priceId) {
		return pr.findOne(priceId);
	}

	@Override
	public Price getPriceByProductId(int productId) {
		return pr.getByProductId(productId);
	}

	@Override
	public boolean createOrUpdatePrice(Price price) {
		Price updatedPrice = pr.save(price);
		boolean status = false;
		if (null != updatedPrice) {
			status = true;
		}
		// sendMessage("UPDATE", updatedPrice);
		return status;
	}

	@Override
	public boolean removePrice(int priceId) {
		Price deletedPrice = pr.findOne(priceId);
		pr.delete(priceId);
		boolean deleted = false;
		if (null != deletedPrice) {
			// sendMessage("DELETE", deletedPrice);
			deleted = true;
		}
		return deleted;
	}

	// private void sendMessage(String action, Price price) {
	// NotificationMessage msg = new NotificationMessage();
	// msg.setNotificationType(NotificationType.UPDATE);
	// if (action.equals("DELETE")) {
	// msg.setNotificationType(NotificationType.DELETE);
	// }
	// msg.setProducerApplication(ProducerApplication.PRICE);
	// msg.setMessage(price);
	// jmsTemplate.convertAndSend(PRICE_UPDATE_QUEUE, msg);
	// }

}
