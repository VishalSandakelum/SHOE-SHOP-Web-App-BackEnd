package lk.ijse.finalcoursework.shoeshop.service.impl;

import lk.ijse.finalcoursework.shoeshop.dto.SupplierDTO;
import lk.ijse.finalcoursework.shoeshop.persistence.entity.Supplier;
import lk.ijse.finalcoursework.shoeshop.persistence.repository.SupplierRepository;
import lk.ijse.finalcoursework.shoeshop.service.SupplierService;
import lk.ijse.finalcoursework.shoeshop.service.execption.DublicateRecordException;
import lk.ijse.finalcoursework.shoeshop.service.execption.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Vishal Sandakelum,
 * @Runtime version: 11.0.11+9-b1341.60 amd64
 **/

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {
    SupplierRepository supplierRepository;
    ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream().map(
                supplier -> modelMapper.map(supplier, SupplierDTO.class)
        ).toList();
    }

    @Override
    public SupplierDTO getSupplierDetails(String id) {
        if(!supplierRepository.existsBySupplierCode(id)){
            throw new NotFoundException("Supplier "+id+" Not Found!");
        }
        return modelMapper.map(supplierRepository.findBySupplierCode(id), SupplierDTO.class);
    }

    @Override
    public SupplierDTO saveSupplier(SupplierDTO supplierDTO) {
        if(supplierRepository.existsBySupplierCode(supplierDTO.getSupplierCode())){
            throw new DublicateRecordException("This Supplier "+supplierDTO.getSupplierCode()+" already exicts...");
        }
        return modelMapper.map(supplierRepository.save(modelMapper.map(
                supplierDTO, Supplier.class)), SupplierDTO.class
        );
    }

    @Override
    public void updateSupplier(String id, SupplierDTO supplierDTO) {
        Supplier existingSupplier = supplierRepository.findBySupplierCode(id);

        if(existingSupplier.getSupplierCode().isEmpty()){
            throw new NotFoundException("Supplier ID"+ id + "Not Found...");
        }

        existingSupplier.setSupplierName(supplierDTO.getSupplierName());
        existingSupplier.setCategory(supplierDTO.getCategory());

        supplierRepository.save(existingSupplier);
    }

    @Override
    public void deleteSupplier(String id) {
        if(!supplierRepository.existsBySupplierCode(id)){
            throw  new NotFoundException("Supplier ID"+ id + "Not Found...");
        }
        supplierRepository.deleteBySupplierCode(id);
    }
}
