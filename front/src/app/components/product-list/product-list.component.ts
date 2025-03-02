import { Component } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HttpResponse } from '@angular/common/http'


interface Product {
  id: number;
  name: string;
  price: number;
}

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent {
  product: Product[] = [];
  productForm: FormGroup;
  editando: boolean = false;
  productoEditando: Product | null = null;

  constructor(private productoService: ProductService, private fb: FormBuilder) {
    this.productForm = this.fb.group({
      id: [null],
      name: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
    });

    this.productoService.list().subscribe(data => {
      this.product = data;
    });
  }

  async saveProduct(): Promise<void> {
    if (this.productForm.valid) {
      const newProduct: Product = this.productForm.value;
      const productId = await this.getProductIdExecute(newProduct.id);
      if(productId==204){//204 no content
         this.saveProductServiceExecute(newProduct);
      }if(productId==200){//200 content
        this.updateProductTable(newProduct);
        this.updateProductServiceExecute(newProduct);
      }

    } else {
      this.productForm.markAllAsTouched();
    }
  }

  getProductIdExecute(id: number): Promise<number> {
    return new Promise((resolve, reject) => {
      this.productoService.getProductId(id).subscribe(
        (response: HttpResponse<void>) => {
          console.log('code response getProductIdExecute:', response.status);
          resolve(response.status);
        },
        error => {
          console.error('error search getProductIdExecute:', error);
          resolve(500);
        }
      );
    });
  }
  
  

  editProduct(product: Product): void {
    this.productForm.setValue(product);
    this.editando = true;
    this.productoEditando = product;
  }

  deleteProduct(id: number): void {
    this.product = this.product.filter(p => p.id !== id);
    this.productoService.delete(id).subscribe(
      (response: HttpResponse<void>) => {
        console.log('code response deleteProduct:', response.status);
        
        this.productForm.reset();
      },
      error => {
        console.error('error delete product:', error);
      }
    );

  }

  async saveProductServiceExecute(newProduct: Product): Promise<void> {
    try {
      const response = await this.productoService.save(newProduct).toPromise();
      console.log('code response saveProductServiceExecute:', response?.status);
      this.product.push(newProduct);
      this.productForm.reset();
    } catch (error) {
      console.error('Error al crear el producto:', error);
    }
  }
  
  async updateProductServiceExecute(newProduct: Product): Promise<void> {
    try {
      const response = await this.productoService.update(newProduct).toPromise();
      console.log('code response updateProductServiceExecute', response?.status);
      this.productForm.reset();
    } catch (error) {
      console.error('error save product:', error);
    }
  }



updateProductTable(updatedProduct: Product): void {
  const productIndex = this.product.findIndex(p => p.id === updatedProduct.id);
  if (productIndex !== -1) {
    this.product[productIndex] = updatedProduct;
    console.log('Producto actualizado:', this.product[productIndex]);
  } else {
    console.error('Producto no encontrado.');
  }
}

// Uso de la función updateProduct
//const updatedProduct: Product = { id: 1, name: 'Nuevo Nombre', price: 99.99 };
//this.updateProduct(updatedProduct);

}
