
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

interface Product {
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

  list(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl);
  }

  save(product: Product): Observable<HttpResponse<Product>> {
    return this.http.post<Product>(this.apiUrl, product, { observe: 'response' });
  }

  delete(productId: number): Observable<HttpResponse<void>> {
    return this.http.delete<void>(`${this.apiUrl}/${productId}`, { observe: 'response' });
  }
  

}
