export class PermissionTreeNode {
  title: string;
  key: string;
  expanded?: boolean;
  isLeaf?: boolean;
  children?: Array<PermissionTreeNode>;
  selectable = false;
  selected = false;
}
