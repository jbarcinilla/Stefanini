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

  saveProduct(): void {
    if (this.productForm.valid) {
      const newProduct: Product = this.productForm.value;
      this.productoService.save(newProduct).subscribe(
        (response: HttpResponse<Product>) => {
          console.log('Código de respuesta:', response.status);
          this.product.push(newProduct);
          this.productForm.reset();
        },
        error => {
          console.error('Error al crear el producto:', error);
        }
      );
    } else {
      this.productForm.markAllAsTouched();
    }
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
        console.log('Código de respuesta:', response.status);
        
        this.productForm.reset();
      },
      error => {
        console.error('Error al crear el producto:', error);
      }
    );

  }
}
