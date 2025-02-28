import { Component } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HttpResponse } from '@angular/common/http'


interface Producto {
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
  productos: Producto[] = [];
  productoForm: FormGroup;
  editando: boolean = false;
  productoEditando: Producto | null = null;

  constructor(private productoService: ProductService, private fb: FormBuilder) {
    this.productoForm = this.fb.group({
      id: [null],
      name: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
    });

    this.productoService.getProduct().subscribe(data => {
      this.productos = data;
    });
  }

  agregarProducto(): void {
    if (this.productoForm.valid) {
      const nuevoProducto: Producto = this.productoForm.value;
      this.productoService.crearProducto(nuevoProducto).subscribe(
        (response: HttpResponse<Producto>) => {
          console.log('Código de respuesta:', response.status);
          this.productoForm.reset();
        },
        error => {
          console.error('Error al crear el producto:', error);
        }
      );
    } else {
      this.productoForm.markAllAsTouched();
    }
  }

  editarProducto(producto: Producto): void {
    this.productoForm.setValue(producto);
    this.editando = true;
    this.productoEditando = producto;
  }

  eliminarProducto(id: number): void {
    this.productos = this.productos.filter(p => p.id !== id);
  }
}
