package lk.custom_process_management.asset.vezzalOrder.controller;

import lk.custom_process_management.asset.item.service.ItemService;
import lk.custom_process_management.asset.vezzalArrivalHistory.service.VezzalArrivalHistoryService;
import lk.custom_process_management.asset.vezzalOrder.entity.Enum.VezzalOrderStatus;
import lk.custom_process_management.asset.vezzalOrder.entity.VezzalOrder;
import lk.custom_process_management.asset.vezzalOrder.service.VezzalOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping( "/vezzalOrder" )
public class VezzalOrderController {
  private final VezzalOrderService vezzalOrderService;
  private final VezzalArrivalHistoryService vezzalArrivalHistoryService;
  private final ItemService itemService;

  public VezzalOrderController(VezzalOrderService vezzalOrderService,
                               VezzalArrivalHistoryService vezzalArrivalHistoryService, ItemService itemService) {
    this.vezzalOrderService = vezzalOrderService;
    this.vezzalArrivalHistoryService = vezzalArrivalHistoryService;
    this.itemService = itemService;
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("vezzalOrders", vezzalOrderService.findAll());
    return "vezzalOreder/vezzalOder";
  }

  private String commonAdd(Model model, VezzalOrder vezzalOrder, boolean addStatus) {
    model.addAttribute("vezzalOrder", vezzalOrder);
    model.addAttribute("addStatus", addStatus);
    //TODO -> need to filter login user if user has privilege to manage relevant vezzal that will be pop up
    model.addAttribute("vezzalOderHistory", vezzalArrivalHistoryService.findAll());
    model.addAttribute("items", itemService.findAll());
    return "vezzalOrder/addVezzalOrder";
  }

  @GetMapping( "/add" )
  public String addForm(Model model) {
    return commonAdd(model, new VezzalOrder(), true);
  }

  public String persist(@Valid @ModelAttribute VezzalOrder vezzalOrder, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model) {
    if ( bindingResult.hasErrors() ) {
      return commonAdd(model, vezzalOrder, true);
    }
    redirectAttributes.addFlashAttribute("vezzalOrderDetail", vezzalOrderService.persist(vezzalOrder));
    return "redirect:/vezzalOrder";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id,RedirectAttributes redirectAttributes, Model model) {
    VezzalOrder vezzalOrder = vezzalOrderService.findById(id);
    if ( vezzalOrder.getVezzalOrderStatus().equals(VezzalOrderStatus.PROCESSING) ) {
      return commonAdd(model, vezzalOrder, false);
    } else {
      redirectAttributes.addFlashAttribute("message", "This order is processing you can not edit further more. \n Hence please contact office who works in Sri Lanka Custom");
      return "redirect:/vezzalOrder";
    }
    //TODO -> first check order is processing or not
    //second logged user is custom or not and order status is valid
    //so then we can give permission to edit custom officers
  }

  //delete method is not applicable this part
  @GetMapping( "/{id}" )
  public String view(@PathVariable Integer id, Model model) {
    model.addAttribute("vezzalOrderDetail", vezzalOrderService.findById(id));
    return "vezzalOrder/vezzalOrder-detail";
  }
}