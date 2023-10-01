import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, translate } from 'react-jhipster';
import { toast } from 'react-toastify';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { ICurrentInventory } from 'app/shared/model/current-inventory.model';
import { getEntity, deleteEntity } from './current-inventory.reducer';

export const CurrentInventoryDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const currentInventoryEntity: ICurrentInventory = useAppSelector(state => state.currentInventory.entity);
  const updateSuccess: boolean = useAppSelector(state => state.currentInventory.updateSuccess);

  const handleClose = () => {
    navigate('/current-inventory' + location.search);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      toast.success(translate("giftandgainFrontendApp.currentInventory.deleted", { id }));
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(currentInventoryEntity.inventoryId));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="currentInventoryDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="giftandgainFrontendApp.currentInventory.delete.question">
        <Translate contentKey="giftandgainFrontendApp.currentInventory.delete.question" interpolate={{ id: currentInventoryEntity.inventoryId }}>
          Are you sure you want to delete this CurrentInventory?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-currentInventory" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default CurrentInventoryDeleteDialog;
