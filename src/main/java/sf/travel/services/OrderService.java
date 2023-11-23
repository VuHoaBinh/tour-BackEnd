package sf.travel.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sf.travel.entities.Order;
import sf.travel.entities.Travel;
import sf.travel.errors.ConflictError;
import sf.travel.errors.ErrorCode;
import sf.travel.helper.specifications.OrderSpec;
import sf.travel.repositories.OrderRepository;
import sf.travel.repositories.TravelRepository;
import sf.travel.rests.types.*;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    @Autowired private final OrderRepository orderRepo;
    @Autowired private final OrderSpec orderSpec;
    @Autowired private final TravelRepository travelRepo;

    public Order create(CreateOrderReq input) {
        Optional<Travel> travel = travelRepo.findById(input.getTravelId());

        Order order = new Order();
        order.setUsername(input.getUsername());
        order.setEmail(input.getEmail());
        order.setTitle(input.getTitle());
        order.setDescription(input.getDescription());
        order.setStatus(input.getStatus());

        if (travel.isPresent()){
            order.setTravel(travel.get());
        } else {
            throw new ConflictError(ErrorCode.TRAVEL_NOT_FOUND);
        }

        return orderRepo.save(order);
    }

    public Page<Order> findAll(OrderFilter filter){
        Specification<Order> spec = null;
        if (filter.getName() != null) {
            spec = orderSpec.createSpecification("name", filter.getName());
        }
        if (filter.getStatus() != null) {
            spec = orderSpec.createSpecification("status", filter.getStatus());
        }
        if (filter.getPrice() != -1) {
            spec = orderSpec.createSpecification("price", filter.getPrice());
        }

        if (filter.getSearchText() != null) {
            spec = orderSpec.createLikeSpecification("description", filter.getSearchText())
                    .or(orderSpec.createLikeSpecification("name", filter.getSearchText()));
        }

        System.out.println("Conditions: " + spec);

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("id").descending());

        Page<Order> res = orderRepo.findAll(spec, pageable);
        return res;
    }

    public Optional<Order> findById(Long id){
        return orderRepo.findById(id);
    }

    public void delete(Long id){
        orderRepo.deleteById(id);
    }
}
