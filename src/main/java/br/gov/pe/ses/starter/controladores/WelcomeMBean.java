/*
 * Copyright 2016-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.gov.pe.ses.starter.controladores;

import java.io.Serializable;

import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Component;

/**
 * Welcome Page.
 *
 * @author Marcelo Fernandes
 */
@Setter
@Getter
@Component
@ViewScoped
public class WelcomeMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String text = "";

}
