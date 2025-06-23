/*
 *  Copyright 2009-2022 PrimeTek.
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package br.gov.pe.ses.starter.controladores.ultima;

import java.util.List;


import br.gov.pe.ses.starter.controladores.ultima.domain.Customer;
import br.gov.pe.ses.starter.controladores.ultima.service.CustomerService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class OverlayDemoView {

    private List<Customer> customers;

    private Customer selectedCustomer;

    @Inject
    private CustomerService service;

    @PostConstruct
    public void init() {
        this.customers = this.service.getCustomers(10);
    }

    public List<Customer> getCustomers() {
        return customers;
    }


    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
}