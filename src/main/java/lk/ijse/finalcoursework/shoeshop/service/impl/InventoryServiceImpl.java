package lk.ijse.finalcoursework.shoeshop.service.impl;

import lk.ijse.finalcoursework.shoeshop.dto.EmployeeDTO;
import lk.ijse.finalcoursework.shoeshop.dto.InventoryDTO;
import lk.ijse.finalcoursework.shoeshop.dto.SalesInventoryDTO;
import lk.ijse.finalcoursework.shoeshop.persistence.entity.Employee;
import lk.ijse.finalcoursework.shoeshop.persistence.entity.Inventory;
import lk.ijse.finalcoursework.shoeshop.persistence.entity.Sales;
import lk.ijse.finalcoursework.shoeshop.persistence.entity.SalesDetails;
import lk.ijse.finalcoursework.shoeshop.persistence.repository.InventoryRepository;
import lk.ijse.finalcoursework.shoeshop.persistence.repository.SalesDetailsRepository;
import lk.ijse.finalcoursework.shoeshop.persistence.repository.SalesRepository;
import lk.ijse.finalcoursework.shoeshop.service.InventoryService;
import lk.ijse.finalcoursework.shoeshop.service.execption.DublicateRecordException;
import lk.ijse.finalcoursework.shoeshop.service.execption.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

/**
 * @author: Vishal Sandakelum,
 * @Runtime version: 11.0.11+9-b1341.60 amd64
 **/

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {
    InventoryRepository inventoryRepository;
    SalesRepository salesRepository;
    SalesDetailsRepository salesDetailsRepository;
    ModelMapper modelMapper;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, ModelMapper modelMapper, SalesRepository salesRepository, SalesDetailsRepository salesDetailsRepository) {
        this.inventoryRepository = inventoryRepository;
        this.modelMapper = modelMapper;
        this.salesRepository = salesRepository;
        this.salesDetailsRepository = salesDetailsRepository;
    }

    @Override
    public List<InventoryDTO> getAllInventory() {
        getMostSaleItem();
        return inventoryRepository.findAll().stream().map(
                inventory -> modelMapper.map(inventory, InventoryDTO.class)
        ).toList();
    }

    @Override
    public InventoryDTO getInventoryDetails(String id) {
        if(!inventoryRepository.existsByItemCode(id)){
            throw new NotFoundException("Inventory "+id+" Not Found!");
        }
        return modelMapper.map(inventoryRepository.findByItemCode(id), InventoryDTO.class);
    }

    @Override
    public InventoryDTO saveInventory(InventoryDTO inventoryDTO) {
        if(inventoryRepository.existsByItemCode(inventoryDTO.getItemCode())){
            throw new DublicateRecordException("This Inventory "+inventoryDTO.getItemCode()+" already exicts...");
        }
        System.out.println(inventoryDTO.getItemCode());
        inventoryDTO.setItemCode(nextInventoryCode(inventoryDTO.getItemCode()));
        return modelMapper.map(inventoryRepository.save(modelMapper.map(
                inventoryDTO, Inventory.class)), InventoryDTO.class
        );
    }

    @Override
    public void updateInventory(String id, InventoryDTO inventoryDTO) {
        Inventory existingInventory = inventoryRepository.findByItemCode(id);

        if(existingInventory.getItemCode().isEmpty()){
            throw new NotFoundException("Inventory "+ id + "Not Found...");
        }

        existingInventory.setItemDescription(inventoryDTO.getItemDescription());
        existingInventory.setItemPicture(inventoryDTO.getItemPicture());

        inventoryRepository.save(existingInventory);
    }

    @Override
    public void deleteInventory(String id) {
        if(!inventoryRepository.existsByItemCode(id)){
            throw  new NotFoundException("Inventory "+ id + "Not Found...");
        }
        inventoryRepository.deleteByItemCode(id);
    }

    @Override
    public String nextInventoryCode(String code) {
        String lastInventoryCode = "IIM"+""+inventoryRepository.countInventoryRows();
        if(lastInventoryCode==null){lastInventoryCode = code+"000";}
        int numericPart = Integer.parseInt(lastInventoryCode.substring(3));
        numericPart++;
        String nextInventoryCode = code + String.format("%03d", numericPart);
        return nextInventoryCode;
    }

    public InventoryDTO getMostSaleItem(){
        List<Sales>getAllTodaySales;
        List<SalesInventoryDTO>getTodaySaleInventoryDetails = new ArrayList<>();
        List<SalesInventoryDTO>TodaySaleInventoryDetails = new ArrayList<>();
        Boolean notFound = false;
        LocalDate today = LocalDate.now();
        getAllTodaySales = salesRepository.findTodaySales(String.valueOf(today));
        System.out.println(getAllTodaySales.get(0).getOrderNo());
        for(int i = 0; i<getAllTodaySales.size(); i++){
            List<SalesInventoryDTO>getOneOrderSalesDetails = salesDetailsRepository.findAllBySalesOrderNo(getAllTodaySales.get(i).getOrderNo()).stream().map(
                    salesDetails -> modelMapper.map(salesDetails, SalesInventoryDTO.class)
            ).toList();
            for(SalesInventoryDTO salesInventoryDTO:getOneOrderSalesDetails){
                getTodaySaleInventoryDetails.add(salesInventoryDTO);
            }
        }
        System.out.println(getTodaySaleInventoryDetails.size());
        for(int i = 0; i<getTodaySaleInventoryDetails.size(); i++){
            if(TodaySaleInventoryDetails.size()>0) {
                L:for (int j = 0; j < TodaySaleInventoryDetails.size(); j++) {
                    if(getTodaySaleInventoryDetails.get(i).getInventory().getItemCode().equals(
                            TodaySaleInventoryDetails.get(j).getInventory().getItemCode()
                    )){
                        System.out.println("comming!");
                        TodaySaleInventoryDetails.get(j).setQuantity(
                                TodaySaleInventoryDetails.get(j).getQuantity()+getTodaySaleInventoryDetails.get(i).getQuantity()
                        );
                        notFound = false;
                        break L;
                    }else {notFound = true;}
                }
                if(notFound){
                    TodaySaleInventoryDetails.add(getTodaySaleInventoryDetails.get(i));
                }
            }else{
                TodaySaleInventoryDetails.add(getTodaySaleInventoryDetails.get(i));
            }
        }
        TodaySaleInventoryDetails = sortAsSaleItemsQuantity(TodaySaleInventoryDetails);
        for(int i = 0; i <TodaySaleInventoryDetails.size(); i++){
            System.out.println(TodaySaleInventoryDetails.get(i).getQuantity());
        }

        return null;
    }

    private List<SalesInventoryDTO> sortAsSaleItemsQuantity(List<SalesInventoryDTO> list){
        list.sort(Comparator.comparingInt(SalesInventoryDTO::getQuantity));
        return list;
    }

}
