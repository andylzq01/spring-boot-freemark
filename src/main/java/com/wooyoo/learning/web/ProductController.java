package com.wooyoo.learning.web;

import com.wooyoo.learning.common.ProductNotFoundException;
import com.wooyoo.learning.dao.domain.Product;
import com.wooyoo.learning.dao.domain.TbUser;
import com.wooyoo.learning.dao.mapper.ProductMapper;
import com.wooyoo.learning.service.HelloRemoteApi;
import com.wooyoo.learning.service.TbUserService;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


//@RestController    @RestController：  @RestController是@Controller和@ResponseBody的结合体，两个标注合并起来的作用。
@Controller
@RequestMapping("/product")
public class ProductController {
    private Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private TbUserService tbUserService;
    @Autowired
    private HelloRemoteApi helloRemoteApi;
    @Autowired
    private CloudSolrServer solrserver;

//    @GetMapping("/{id}")
//    public String  getProductInfo(
//            @PathVariable("id")
//                    Long productId,Model view) {
//        logger.info("请求-----------------------" + productId);
//        System.out.println("==========================");
//        Product product =  productMapper.select(productId);
//        view.addAttribute("product",product);
//        return "index";
//    }
//
//    @PutMapping("/{id}")
//    @ResponseBody
//    public Product updateProductInfo(
//            @PathVariable("id")
//                    Long productId,
//            @RequestBody
//                    Product newProduct) {
//        Product product = productMapper.select(productId);
//        if (product == null) {
//            throw new ProductNotFoundException(productId);
//        }
//        product.setName(newProduct.getName());
//        product.setPrice(newProduct.getPrice());
//        productMapper.update(product);
//        return product;
//    }

    @RequestMapping("/index")
    public ModelAndView index(Model model) {

        ModelAndView mv = new ModelAndView("index");
        logger.info("请求-----------------------index,远程调用------");
        Integer result = helloRemoteApi.add(2, 5);
        logger.info("请求-----------------------index,响应结果------" + result);
        return mv;

    }

    @RequestMapping("/user")
    public String toindex(Model model) {
        logger.info("请求-----------------------index1111");
        TbUser tbUser = tbUserService.selectByPrimaryKey(10L);
        model.addAttribute("tbUser", tbUser);
        return "user";

    }

    @RequestMapping("/test")
    public void test() {
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.add("q", "demo:父亲");
        params.add("ws", "json");
        params.add("start", "0");
        params.add("rows", "10");
        QueryResponse response = null;

        try {
            response = solrserver.query(params);
            SolrDocumentList results = response.getResults();
            for (SolrDocument document : results) {
                System.out.println(document.getFieldValue("demo"));
                System.out.println(document.getFieldValue("id"));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        System.out.println(response.toString());
    }
}
