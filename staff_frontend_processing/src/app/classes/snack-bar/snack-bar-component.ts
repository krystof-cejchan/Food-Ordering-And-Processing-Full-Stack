import { Component } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";

export function openSnackBar(snackBar: MatSnackBar, durationInSeconds: number = 2.5) {
  snackBar.openFromComponent(SnackBarComponent, {
    duration: durationInSeconds * 1000,
  });
}


@Component({
  selector: 'snack-bar-component-example-snack',
  templateUrl: './snack-bar.html',
  styles: [],
})
export class SnackBarComponent { }
