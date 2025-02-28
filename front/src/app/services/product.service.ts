
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

interface Producto {
  id: number;
  name: string;
  price: number;
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private apiUrl = 'http://localhost:8088/api/products';

  constructor(private http: HttpClient) {}

  getProduct(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.apiUrl);
  }

  crearProducto(producto: Producto): Observable<HttpResponse<Producto>> {
    return this.http.post<Producto>(this.apiUrl, producto, { observe: 'response' });
  }
}
