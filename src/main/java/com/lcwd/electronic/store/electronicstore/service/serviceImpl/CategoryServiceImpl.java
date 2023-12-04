package com.lcwd.electronic.store.electronicstore.service.serviceImpl;

import com.lcwd.electronic.store.electronicstore.constants.AppConstant;
import com.lcwd.electronic.store.electronicstore.dtos.CategoryDto;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.entity.Category;
import com.lcwd.electronic.store.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.electronicstore.helper.Helper;
import com.lcwd.electronic.store.electronicstore.repository.CategoryRepository;
import com.lcwd.electronic.store.electronicstore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${category.cover.image.path}")
    private String coverImagepath;

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        String cateId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(cateId);
        Category category = this.modelMapper.map(categoryDto, Category.class);
        logger.info("Initiating the dao call to save catergory data");
        this.categoryRepository.save(category);
        logger.info("Completed the dao call to save category data");
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        logger.info("Initiating the dao call to update data with categoryId:{}", categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category category1 = this.categoryRepository.save(category);
        logger.info("Completed the dao call to update data with categoryId:{}", categoryId);
        return this.modelMapper.map(category1, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        logger.info("Initiating the dao call for retrived data with categoryId:{}", categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        logger.info("Completed the dao call to retrived data with categoryId:{}", categoryId);
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        logger.info("Initiating dao call to retrived all data");
        Page<Category> page = this.categoryRepository.findAll(p);
        logger.info("Completed dao call to retrived all data");
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;
    }


    @Override
    public void deleteCategory(String categoryId) {
        logger.info("Initiating dao call to deleted data with categoryId:{}", categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        String path = coverImagepath + category.getCoverImage();
        try {
            Path path1 = Paths.get(path);
            Files.delete(path1);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        logger.info("completed dao call to deleted data with categoryId:{}", categoryId);
        this.categoryRepository.delete(category);


    }
}