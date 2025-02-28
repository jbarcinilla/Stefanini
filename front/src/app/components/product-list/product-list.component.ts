import { Component } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


interface Producto {
  id: number;
  nombre: string;
  precio: number;
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
      nombre: ['', Validators.required],
      precio: ['', [Validators.required, Validators.min(0)]],
    });

    this.productoService.getProductos().subscribe(data => {
      this.productos = data;
    });
  }

  agregarProducto(): void {
    if (this.productoForm.valid) {
      if (this.editando) {
        this.productoEditando!.nombre = this.productoForm.value.nombre;
        this.productoEditando!.precio = this.productoForm.value.precio;
        this.productoEditando!.id = this.productoForm.value.id;
        this.editando = false;
        this.productoEditando = null;
      } else {
        const nuevoProducto: Producto = this.productoForm.value;
        this.productos.push(nuevoProducto);
      }
      this.productoForm.reset();
    } else {
      // Muestra errores de validación
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
