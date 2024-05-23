package lk.ijse.finalcoursework.shoeshop.service.impl;

import lk.ijse.finalcoursework.shoeshop.dto.CustomerDTO;
import lk.ijse.finalcoursework.shoeshop.dto.InventoryDTO;
import lk.ijse.finalcoursework.shoeshop.dto.SalesDTO;
import lk.ijse.finalcoursework.shoeshop.dto.SalesInventoryDTO;
import lk.ijse.finalcoursework.shoeshop.persistence.entity.*;
import lk.ijse.finalcoursework.shoeshop.persistence.repository.InventoryRepository;
import lk.ijse.finalcoursework.shoeshop.persistence.repository.SalesDetailsRepository;
import lk.ijse.finalcoursework.shoeshop.persistence.repository.SalesRepository;
import lk.ijse.finalcoursework.shoeshop.service.SaleService;
import lk.ijse.finalcoursework.shoeshop.service.execption.DublicateRecordException;
import lk.ijse.finalcoursework.shoeshop.service.execption.NotFoundException;
import lk.ijse.finalcoursework.shoeshop.service.execption.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Vishal Sandakelum,
 * @Runtime version: 11.0.11+9-b1341.60 amd64
 **/

@Service
@Transactional
public class SaleServiceImpl implements SaleService {
    SalesRepository salesRepository;
    SalesDetailsRepository salesDetailsRepository;
    InventoryRepository inventoryRepository;
    ModelMapper modelMapper;

    public SaleServiceImpl(SalesRepository salesRepository, SalesDetailsRepository salesDetailsRepository, ModelMapper modelMapper,InventoryRepository inventoryRepository) {
        this.salesRepository = salesRepository;
        this.salesDetailsRepository = salesDetailsRepository;
        this.modelMapper = modelMapper;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<SalesDTO> getAllSales() {
        List<Sales> salesList = salesRepository.findAll();
        return salesList.stream().map(sales -> {
            SalesDTO salesDTO = modelMapper.map(sales, SalesDTO.class);

            List<SalesDetails> salesDetailsList = salesDetailsRepository.findAllBySalesOrderNo(sales.getOrderNo());
            List<SalesInventoryDTO> salesInventoryDTOList = salesDetailsList.stream()
                    .map(details -> {
                        SalesInventoryDTO salesInventoryDTO = modelMapper.map(details, SalesInventoryDTO.class);
                        salesInventoryDTO.setInventory(modelMapper.map(details.getInventory(), InventoryDTO.class));
                        return salesInventoryDTO;
                    })
                    .collect(Collectors.toList());

            salesDTO.setInventory(salesInventoryDTOList);
            return salesDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public SalesDTO getSaleDetails(String id) {
        if(!salesRepository.existsByOrderNo(id)){
            throw new NotFoundException("Sales "+id+" Not Found!");
        }
        SalesDTO salesDTO = modelMapper.map(salesRepository.findByOrderNo(id), SalesDTO.class);
        System.out.println("ID-----------------------"+id);
        List<SalesInventoryDTO> salesInventory = salesDetailsRepository.findAllBySalesOrderNo(id).stream().map(
                salesDetails -> modelMapper.map(salesDetails, SalesInventoryDTO.class)
        ).toList();
        salesDTO.setInventory(salesInventory);

        return salesDTO;
    }

    @Transactional
    @Override
    public SalesDTO saveSales(SalesDTO salesDTO) {
        if(maintainInventoryQuantity(salesDTO)){
            if(salesRepository.existsByOrderNo(salesDTO.getOrderNo())){
                throw new DublicateRecordException("This Sales "+salesDTO.getOrderNo()+" already exicts...");
            }
            SalesDTO newsalesDTO = modelMapper.map(salesRepository.save(modelMapper.map(
                    salesDTO, Sales.class)), SalesDTO.class
            );

            List<SalesInventoryDTO> salesInventoryDTO = new ArrayList<>();
            for (SalesInventoryDTO inventoryDTO : salesDTO.getInventory()) {
                SalesDetails savedSaleDetails = salesDetailsRepository.save(modelMapper.map(inventoryDTO, SalesDetails.class));
                salesInventoryDTO.add(modelMapper.map(savedSaleDetails, SalesInventoryDTO.class));
            }
            newsalesDTO.setInventory(salesInventoryDTO);
            return newsalesDTO;
        }else {
            return salesDTO;
        }
    }

    @Transactional
    @Override
    public void updateSales(String id, SalesDTO salesDTO) {
        for(SalesInventoryDTO inventoryDTO : salesDTO.getInventory()){
            if(inventoryDTO.getQuantity() == 0){
                if(!isDateWithinThreeDays(String.valueOf(salesDTO.getPurchaseDate()))){
                    System.out.println("----------------------------------------------------------------");
                    System.out.println("comming");
                    throw new NotFoundException("Update Failed This Order " +
                            salesDTO.getOrderNo() + " Can't refund");
                }
            }
        }
        if(salesRepository.existsById(salesDTO.getOrderNo())){
            salesRepository.save(modelMapper.map(salesDTO,Sales.class));
            for (SalesInventoryDTO inventoryDTO : salesDTO.getInventory()) {
                if(!salesDetailsRepository.existsById(inventoryDTO.getId())){
                    throw new NotFoundException("Update Failed; Sales id: " +
                            salesDTO.getOrderNo() + " does not exist");
                }
                salesDetailsRepository.save(modelMapper.map(inventoryDTO, SalesDetails.class));
            }
        }
    }

    @Override
    public void deleteSales(String id) {
        if(!salesDetailsRepository.existsBySalesOrderNo(id)&&!salesRepository.existsByOrderNo(id)){
            throw  new NotFoundException("Sales "+ id + "Not Found...");
        }else if(salesRepository.existsByOrderNo(id)){
            salesRepository.deleteByOrderNo(id);
        }
        salesDetailsRepository.deleteAllBySalesOrderNo(id);
        salesRepository.deleteByOrderNo(id);
    }

    @Override
    public String nextOrderCode() {
        String lastOrderCode = salesRepository.findLatestOrderCode();
        if(lastOrderCode==null){lastOrderCode = "ORD0000";}
        int numericPart = Integer.parseInt(lastOrderCode.substring(4));
        numericPart++;
        String nextOrderCode = "ORD" + String.format("%04d", numericPart);
        return nextOrderCode;
    }

    protected boolean isDateWithinThreeDays(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy");
        LocalDateTime inputDate = LocalDateTime.parse(dateString, formatter.withZone(ZoneId.of("Asia/Kolkata")));
        LocalDateTime currentDate = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        LocalDateTime threeDaysAgo = currentDate.minus(3, ChronoUnit.DAYS);
        return !inputDate.isBefore(threeDaysAgo);
    }

    protected Boolean maintainInventoryQuantity(SalesDTO salesDTO){
        boolean valid = false;
        for (int i = 0; i<salesDTO.getInventory().size();i++){
            SalesInventoryDTO inventoryDTO = salesDTO.getInventory().get(i);
            String itemCode = inventoryDTO.getInventory().getItemCode();
            System.out.println(itemCode);

            InventoryDTO inventory = modelMapper.map(inventoryRepository.findByItemCode(itemCode),InventoryDTO.class);
            if(inventory.getQuantity()>0){
                if(inventory.getQuantity()-inventoryDTO.getQuantity()>=0){
                    inventory.setQuantity(inventory.getQuantity()-inventoryDTO.getQuantity());
                    inventoryRepository.save(modelMapper.map(inventory, Inventory.class));
                    valid = true;
                }else{
                    valid = false;
                    throw new ServiceException("Can't Proceed this Sale ."+inventory.getItemDescription()+" No much Quantity");
                }
            }else{
                valid = false;
                throw new ServiceException("Can't Proceed this Sale ."+inventory.getItemDescription()+" No much Quantity");
            }
        }
        return valid;
    }
}
