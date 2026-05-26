package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.CategoryService;
import co.edu.usbcali.ecommerceusb.dto.CategoryResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCategoryRequest;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.InternalServerErrorException;
import co.edu.usbcali.ecommerceusb.exception.NotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.CategoryMapper;
import co.edu.usbcali.ecommerceusb.model.Category;
import co.edu.usbcali.ecommerceusb.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) return List.of();
        return CategoryMapper.modelToCategoryResponseList(categories);
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para buscar");
        }
        return categoryRepository.findById(id)
                .map(CategoryMapper::modelToCategoryResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Categoría no encontrada con el id: %d", id)));
    }

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto CreateCategoryRequest no puede ser nulo.");
        }
        if (Objects.isNull(request.getName()) || request.getName().isBlank()) {
            throw new BadRequestException("El campo name no puede ser nulo.");
        }
        if (categoryRepository.existsByName(request.getName())) {
            throw new InternalServerErrorException("Ya existe una categoría con ese nombre.");
        }
        Category parent = null;
        if (request.getParentId() != null) {
            if (request.getParentId() <= 0) {
                throw new BadRequestException("El campo parentId debe ser mayor a 0.");
            }
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new NotFoundException("Categoría padre no encontrada."));
        }
        try {
            Category category = CategoryMapper.createCategoryRequestToCategory(request, parent);
            category = categoryRepository.save(category);
            return CategoryMapper.modelToCategoryResponse(category);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al guardar la categoría: " + e.getMessage());
        }
    }

    @Override
    public CategoryResponse updateCategory(Integer id, CreateCategoryRequest request) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para actualizar");
        }
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto CreateCategoryRequest no puede ser nulo.");
        }
        if (Objects.isNull(request.getName()) || request.getName().isBlank()) {
            throw new BadRequestException("El campo name no puede ser nulo.");
        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Categoría no encontrada con el id: %d", id)));
        if (!category.getName().equals(request.getName()) &&
                categoryRepository.existsByName(request.getName())) {
            throw new InternalServerErrorException("Ya existe una categoría con ese nombre.");
        }
        Category parent = null;
        if (request.getParentId() != null) {
            if (request.getParentId().equals(id)) {
                throw new BadRequestException("Una categoría no puede ser su propio padre.");
            }
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new NotFoundException("Categoría padre no encontrada."));
        }
        try {
            category.setName(request.getName());
            category.setParent(parent);
            category = categoryRepository.save(category);
            return CategoryMapper.modelToCategoryResponse(category);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al actualizar la categoría: " + e.getMessage());
        }
    }

    @Override
    public void deleteCategory(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para eliminar");
        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Categoría no encontrada con el id: %d", id)));
        try {
            categoryRepository.delete(category);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al eliminar la categoría: " + e.getMessage());
        }
    }
}